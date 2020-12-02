package com.gwerner.Arline.Manager.services.mappers;

import com.gwerner.Arline.Manager.DTO.AircraftDTO;
import com.gwerner.Arline.Manager.services.data.Aircraft;
import com.gwerner.Arline.Manager.services.entities.AircraftEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AircraftMapper {
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    Aircraft fromDto(AircraftDTO aircraft);

    Aircraft fromEntity(AircraftEntity aircraft);
    AircraftEntity toEntity(Aircraft aircraft);
}
