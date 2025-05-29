package com.example.demo.mapstruct;

import java.util.List;
import com.example.demo.data.dto.DiscenteDTO;
import com.example.demo.data.dto.DiscenteDTOLight;
import com.example.demo.data.entity.Discente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscenteMapper {
    DiscenteDTO toDto(Discente discente);
    Discente toEntity(DiscenteDTO dto);

    List<DiscenteDTO> toDtoList(List<Discente> discenti); //aggiunto ora

//    DiscenteDTOLight toDtoLight(Discente discente);
//    Discente toEntityLight(DiscenteDTOLight dto);
}