package com.gwerner.Arline.Manager.services;

import com.gwerner.Arline.Manager.services.data.Aircraft;
import com.gwerner.Arline.Manager.services.data.Airline;
import com.gwerner.Arline.Manager.services.data.Location;
import com.gwerner.Arline.Manager.services.mappers.AircraftMapper;
import com.gwerner.Arline.Manager.services.mappers.AirlineMapper;
import com.gwerner.Arline.Manager.services.repository.AircraftRepository;
import com.gwerner.Arline.Manager.services.repository.AirlineRepository;
import com.gwerner.Arline.Manager.services.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Consumer;

@Slf4j
@Service
public class ValidationService {
    private AirlineRepository airlineRepository;
    private AircraftRepository aircraftRepository;
    private LocationRepository locationRepository;
    private AirlineMapper airlineMapper;
    private AircraftMapper aircraftMapper;
    private static Logger logger = LoggerFactory.getLogger(AirLineManagerService.class);

    public ValidationService(AirlineRepository airlineRepository, AircraftRepository aircraftRepository, LocationRepository locationRepository, AirlineMapper airlineMapper, AircraftMapper aircraftMapper) {
        this.airlineRepository = airlineRepository;
        this.aircraftRepository = aircraftRepository;
        this.locationRepository = locationRepository;
        this.airlineMapper = airlineMapper;
        this.aircraftMapper = aircraftMapper;
    }

    public void validateAirlineDoesntExist(Airline airline) {
        airlineRepository.findById(airline.getName()).ifPresent(airlineEntity->{
            logger.error("Airline "+ airline.getName() + " already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Airline "+ airline.getName() + " already exists");
        });
    }

    public void validateAirlineExists(String airline) {
        airlineRepository.findById(airline).orElseThrow(()-> {
            logger.error("Airline " + airline + " doesn't exist");
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Airline " + airline + " doesn't exist");
        });
    }

    public void validateLocationDoesntExist(Location location) {
        if(locationRepository.findByNameOrLatitudeAndLongitude(location.getName(),location.getLatitude(),location.getLongitude()).size()>0){
            logger.error("location already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"location already exists");
        }
    }

    public void validateAircraftExistsInAirline(String airline, String aircraft) {
        aircraftRepository.findById(aircraft).map(aircraftEntity -> aircraftMapper.fromEntity(aircraftEntity))
                .map(aircraft1 -> aircraft1.getAirline().equals(airline)?aircraft1:null)
                .orElseThrow(()->{
                    logger.error("Aircraft "+ aircraft + " doesn't exist in " + airline);
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST,"Aircraft "+ aircraft + " doesn't exist in " + airline );
                });
    }

    public void validateAircraftDoesntExist(Aircraft aircraft) {
        aircraftRepository.findById(aircraft.getName()).ifPresent(airlineEntity->{
            logger.error("Aircraft "+ aircraft + " already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Aircraft "+ aircraft + " already exists");
        });
    }
}
