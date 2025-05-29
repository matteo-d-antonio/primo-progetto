package com.example.demo.mapstruct;

import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.dto.DocenteDTO;
import com.example.demo.data.dto.DocenteDTOLight;
import com.example.demo.data.entity.Discente;
import com.example.demo.data.entity.Docente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocenteMapper {
    DocenteDTO toDto(Docente docente);
    Docente toEntity(DocenteDTO dto);

//    DocenteDTOLight toDtoLight(Docente docente);
//    Docente toEntityLight(DocenteDTOLight dto);
}