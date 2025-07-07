package com.example.demo.mapstruct;

import com.example.demo.data.dto.CorsoDTO;
import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.entity.Corso;
import com.example.demo.data.entity.Discente;
import com.example.demo.data.entity.Docente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DocenteMapper.class, DiscenteMapper.class})
public interface CorsoMapper {

    //@Mapping(target = "id", ignore= true)
    @Mapping(target = "docenteDTOLight" , source = "docente")
    @Mapping(target = "discentiDTOLight", expression = "java(corso.getDiscenti().stream().map(d -> new com.example.demo.data.dto.DiscenteDTOLight(d.getNome(), d.getCognome())).toList())")
    CorsoDTO corsoToDto(Corso corso);

    @Mapping(target = "id", ignore= true)
    @Mapping(target = "docente", ignore = true)
    @Mapping(target = "discenti", ignore = true)
    Corso corsoToEntity(CorsoDTO corsoDTO);
}