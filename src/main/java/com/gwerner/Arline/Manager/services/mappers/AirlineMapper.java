package com.gwerner.Arline.Manager.services.mappers;

import com.gwerner.Arline.Manager.DTO.*;
import com.gwerner.Arline.Manager.services.data.*;

import com.gwerner.Arline.Manager.services.entities.AirlineEntity;
import com.gwerner.Arline.Manager.services.entities.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    @Mapping(source = "budget",target = "balance")
    Airline fromDto(AirlineDTO airline);
    AirlineBalanceDTO toDto(AirlineBalance balance);
    AirlineBalanceListDTO toDto(AirLineBalanceList list);
    AirlineDistanceListDTO toDto(AirlineDistanceList list);
    LocationDTO toDto(Location location);
    Location fromDto(LocationDTO location);
    LocationDistanceDTO toDto(LocationDistance locationDistance);
    AvailableDestinationsDTO toDto(AvailableDestinations availableDestinations);
    Location fromEntity(LocationEntity locationEntity);
    LocationEntity toEntity(Location location);
    Airline fromEntity(AirlineEntity airlineEntity);
    AirlineEntity toEntity(Airline airline);
}
