package com.example.demo.service;

import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.data.entity.Discente;
import com.example.demo.mapstruct.DiscenteMapper;
import com.example.demo.repository.CorsoRepository;
import com.example.demo.repository.DiscenteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscenteService {
    @Autowired
    DiscenteRepository discenteRepository;

    @Autowired
    CorsoRepository corsoRepository;
    
    @Autowired
    DiscenteMapper discenteMapper;

    public List<DiscenteDTO> findAll() {
        List<Discente> discenti = discenteRepository.findAll();
        return discenteMapper.toDtoList(discenti);
    }

//    public List<DiscenteDTO> findAll() {
//        return discenteRepository.findAll()
//        .stream().map(discenteMapper::toDto).collect(Collectors.toList());
//    }

    public DiscenteDTO get(Long id) {
        Discente discente = discenteRepository.findById(id).orElseThrow();
        return discenteMapper.toDto(discente);
    }

    public DiscenteDTO save(DiscenteDTO d){
        Discente discente = discenteMapper.toEntity(d);
        Discente savedDiscente=discenteRepository.save(discente);
        return discenteMapper.toDto(savedDiscente);
    }

    public DiscenteDTO update(Long id, DiscenteDTO discenteDTO) {
        Discente discente = discenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discente non trovato"));
        if(discenteDTO.getNome()!=null) discente.setNome(discenteDTO.getNome());
        if(discenteDTO.getCognome()!=null) discente.setCognome(discenteDTO.getCognome());
        if(discenteDTO.getMatricola()!=null) discente.setMatricola(discenteDTO.getMatricola());
        if(discenteDTO.getCittaResidenza()!=null) discente.setCittaResidenza(discenteDTO.getCittaResidenza());
        Discente savedDiscente = discenteRepository.save(discente);
        return discenteMapper.toDto(savedDiscente);
    }

    @Transactional
    public void delete(Long id){
        Discente discente = discenteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discente non trovato"));

        List<Corso> corsi = corsoRepository.findByDiscente(discente);
        for (Corso corso : corsi) {
            corso.getDiscenti().remove(discente);
        }
        corsoRepository.saveAll(corsi);

        discenteRepository.delete(discente);

    }

    public List<DiscenteDTO> findByName(String nome) {
        return discenteRepository.findByName(nome).stream()
                .map(discenteMapper::toDto)
                .collect(Collectors.toList());
    }
}
