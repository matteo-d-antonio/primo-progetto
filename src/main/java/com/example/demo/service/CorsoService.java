package com.example.demo.service;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.DiscenteDTOLight;
import com.example.demo.data.dto.DocenteDTOLight;
import com.example.demo.data.entity.Corso;
import com.example.demo.data.entity.Discente;
import com.example.demo.data.entity.Docente;
import com.example.demo.mapstruct.CorsoMapper;
import com.example.demo.repository.CorsoRepository;
import com.example.demo.repository.DiscenteRepository;
import com.example.demo.repository.DocenteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections; // Import per Collections.emptyList()
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream; // Import per Stream

@Service
public class CorsoService {

    @Autowired
    CorsoRepository corsoRepository;
    @Autowired
    CorsoMapper corsoMapper;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private DiscenteRepository discenteRepository;



    //metodi per controllare esistenza discente o docente per creazione/update
    private Docente getOrCreateDocente(DocenteDTOLight docenteDTOLight) {
        if(docenteDTOLight == null || (docenteDTOLight.getNome() == null && docenteDTOLight.getCognome() == null)) {
            return null;
        }
        Docente docente = docenteRepository.findByNomeAndCognome(docenteDTOLight.getNome(), docenteDTOLight.getCognome());
        if(docente == null) {
            docente = new Docente();
            docente.setNome(docenteDTOLight.getNome());
            docente.setCognome(docenteDTOLight.getCognome());
            docente = docenteRepository.save(docente);
        }
        return docente;
    }


    private List<Discente> getOrCreateDiscenti(List<DiscenteDTOLight> discentiDtoLightList) {
        if (discentiDtoLightList == null || discentiDtoLightList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Discente> discentiFinali = new ArrayList<>();
        for (DiscenteDTOLight dtoLight : discentiDtoLightList) {
            if (dtoLight.getNome() == null && dtoLight.getCognome() == null) continue;

            List<Discente> discentiTrovati = discenteRepository.findByNomeAndCognome(
                    dtoLight.getNome(),
                    dtoLight.getCognome()
            );
            if (discentiTrovati == null || discentiTrovati.isEmpty()) {
                Discente nuovoDiscente = new Discente();
                nuovoDiscente.setNome(dtoLight.getNome());
                nuovoDiscente.setCognome(dtoLight.getCognome());
                discentiFinali.add(discenteRepository.save(nuovoDiscente));
            } else {
                discentiFinali.add(discentiTrovati.get(0));
            }
        }
        return discentiFinali;
    }



    //metodi per crud
    @EntityGraph(attributePaths = {"docente", "discente"})
    public List<CorsoDTO> findAll() {
        return corsoRepository.findAll().stream()
                .map(corsoMapper::corsoToDto)
                .collect(Collectors.toList());
    }


    public CorsoDTO get(Long id) {
        Corso corso =corsoRepository.findById(id).orElseThrow();
        return corsoMapper.corsoToDto(corso);
    }


    @EntityGraph(attributePaths = {"docente", "discente"})
    public CorsoDTO save(CorsoDTO c){
        Corso corso = corsoMapper.corsoToEntity(c);


            if (c.getDocenteDTOLight() != null) {
                corso.setDocente(getOrCreateDocente(c.getDocenteDTOLight()));
            } else {
                corso.setDocente(null); // necessario se è nullable
            }

            if (c.getDiscentiDTOLight() != null) {
                corso.setDiscenti(getOrCreateDiscenti(c.getDiscentiDTOLight()));
            } else {
                corso.setDiscenti(null); // opzionale
            }

        Corso savedCorso = corsoRepository.save(corso);
        return corsoMapper.corsoToDto(savedCorso);
    }

    public CorsoDTO update(Long id, CorsoDTO corsoDTO) {
        Corso corso=corsoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Corso non trovato"));

        if(corsoDTO.getNome()!=null) corso.setNome(corsoDTO.getNome());
        if (corsoDTO.getAnnoAccademico()!=null) corso.setAnnoAccademico(corsoDTO.getAnnoAccademico());

        if(corsoDTO.getDocenteDTOLight()!=null) corso.setDocente(getOrCreateDocente(corsoDTO.getDocenteDTOLight()));
        if(corsoDTO.getDiscentiDTOLight()!=null) corso.setDiscenti(getOrCreateDiscenti(corsoDTO.getDiscentiDTOLight()));

        Corso savedCorso = corsoRepository.save(corso);
        return corsoMapper.corsoToDto(savedCorso);
    }


    public void delete(Long id) {
        corsoRepository.deleteById(id);
    }


}