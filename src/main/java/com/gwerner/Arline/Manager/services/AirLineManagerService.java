package com.gwerner.Arline.Manager.services;

import com.gwerner.Arline.Manager.services.data.*;
import com.gwerner.Arline.Manager.services.entities.AircraftEntity;
import com.gwerner.Arline.Manager.services.mappers.AircraftMapper;
import com.gwerner.Arline.Manager.services.mappers.AirlineMapper;
import com.gwerner.Arline.Manager.services.repository.AircraftRepository;
import com.gwerner.Arline.Manager.services.repository.AirlineRepository;
import com.gwerner.Arline.Manager.services.repository.LocationRepository;
import com.peertopark.java.geocalc.Coordinate;
import com.peertopark.java.geocalc.EarthCalc;
import com.peertopark.java.geocalc.Point;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class AirLineManagerService {
    private static Logger logger = LoggerFactory.getLogger(AirLineManagerService.class);
    private AirlineRepository airlineRepository;
    private AircraftRepository aircraftRepository;
    private LocationRepository locationRepository;
    private AirlineMapper airlineMapper;
    private AircraftMapper aircraftMapper;
    private ValidationService validationService;

    public AirLineManagerService(AirlineRepository airlineRepository, AircraftRepository aircraftRepository, LocationRepository locationRepository, AirlineMapper airlineMapper, AircraftMapper aircraftMapper, ValidationService validationService) {
        this.airlineRepository = airlineRepository;
        this.aircraftRepository = aircraftRepository;
        this.locationRepository = locationRepository;
        this.airlineMapper = airlineMapper;
        this.aircraftMapper = aircraftMapper;
        this.validationService = validationService;
    }
    @Transactional
    public void addAirline(Airline airline) {
        validationService.validateAirlineDoesntExist(airline);
        airlineRepository.save(airlineMapper.toEntity(airline));
        locationRepository.save(airlineMapper.toEntity(airline.getHomeBase()));
        logger.info("added new airline: "+ airline.getName());
        logger.info("added new location: "+ airline.getHomeBase().getName());
    }

    public AirLineBalanceList getAirlineBalance() {
        return new AirLineBalanceList(StreamSupport.stream(airlineRepository.findAll().spliterator(),false).map(entity->airlineMapper.fromEntity(entity))
                .map(airline->new AirlineBalance(airline.getName(),airline.getBalance())).collect(Collectors.toList()));
    }
    @Transactional
    public void addAirCraft(String airline,Aircraft aircraft) {
        validationService.validateAirlineExists(airline);
        validationService.validateAircraftDoesntExist(aircraft);
        aircraft.setAirline(airline);
        aircraft.setStartTime(LocalDate.now());
        aircraftRepository.save(aircraftMapper.toEntity(aircraft));
        logger.info("added new aircraft: "+ aircraft.getName()+ " to airline: "+ airline);

    }
    @Transactional
    public void sellAirCraft(String airline, String aircraft) {
        validationService.validateAirlineExists(airline);
        validationService.validateAircraftExistsInAirline(airline,aircraft);
        Optional<AircraftEntity> aircraftEntity = aircraftRepository.findById(aircraft);
        aircraftEntity.map(entity -> aircraftMapper.fromEntity(entity)).flatMap( entity -> airlineRepository.findById(airline).map(airlineEntity -> airlineMapper.fromEntity(airlineEntity))
                .map(airlineEntity -> {
            if(!entity.getAirline().equals(airline)){
                return null;
            }
            airlineEntity.setBalance(airlineEntity.getBalance()+entity.getPrice()*(1 - Period.between(entity.getStartTime(),LocalDate.now()).toTotalMonths()*0.02));
            airlineRepository.save(airlineMapper.toEntity(airlineEntity));
            aircraftRepository.deleteById(aircraft);
            return true;
        })).orElseThrow(()->{
            logger.error("Airline "+ airline + " has no aircraft called " + aircraft);
            return new ResponseStatusException(HttpStatus.NO_CONTENT,"Airline "+ airline + " has no aircraft called " + aircraft);
        });
        logger.info("airline: "+ airline + " sold aircraft: "+ aircraft);
    }

    public void addLocation(Location location) {
        validationService.validateLocationDoesntExist(location);
        locationRepository.save(airlineMapper.toEntity(location));
        logger.info("added new location: "+ location.getName());
    }

    public AirlineDistanceList getAirlineDistance(String airline) {
        validationService.validateAirlineExists(airline);
       return airlineRepository.findById(airline).map(airlineEntity -> airlineMapper.fromEntity(airlineEntity)).map(airline1 -> {
           Point start =  Point.build(airline1.getHomeBase().getLatitude(),airline1.getHomeBase().getLongitude());
           return new AirlineDistanceList(StreamSupport.stream(locationRepository.findAll().spliterator(),false)
                   .map(locationEntity -> airlineMapper.fromEntity(locationEntity))
                   .map(location->
                   new LocationDistance(location.getName(),
                           EarthCalc.getDistance(start,Point.build(location.getLatitude(),location.getLongitude()))/1000))
                   .collect(Collectors.toList()));
        }).orElseGet(()->new AirlineDistanceList(new ArrayList<LocationDistance>()));
    }

    public AvailableDestinations getDestinations(String airline) {
        validationService.validateAirlineExists(airline);
        int maxRange = aircraftRepository.findByAirline(airline).stream()
                .map(aircraftEntity -> aircraftMapper.fromEntity(aircraftEntity))
                .mapToInt(Aircraft::getMaxRange)
                .max().orElseThrow(()-> {
                    logger.error("Airline " + airline + " has no aircrafts");
                    return new ResponseStatusException(HttpStatus.NO_CONTENT, "Airline " + airline + " has no aircrafts");
                });
        return  new AvailableDestinations(getAirlineDistance(airline).getDistances().stream()
                .filter(locationDistance -> locationDistance.getDistance()<=maxRange)
                .map(LocationDistance::getName).collect(Collectors.toList()));
    }

    @Transactional
    public void buyAircraft(String buyer, String seller, String aircraft) {
        validationService.validateAirlineExists(buyer);
        validationService.validateAirlineExists(seller);
        validationService.validateAircraftExistsInAirline(seller,aircraft);
        Optional<Aircraft> aircraftEntity = aircraftRepository.findById(aircraft).map(entity -> aircraftMapper.fromEntity(entity));
        sellAirCraft(seller,aircraft);
        aircraftEntity.flatMap( entity -> airlineRepository.findById(buyer).map(airlineEntity -> airlineMapper.fromEntity(airlineEntity))
                .map(airlineEntity -> {
                    airlineEntity.setBalance(airlineEntity.getBalance()-entity.getPrice()*(1 - Period.between(entity.getStartTime(),LocalDate.now()).toTotalMonths()*0.02));
                    entity.setAirline(buyer);
                    aircraftRepository.save(aircraftMapper.toEntity(entity));
                    airlineRepository.save(airlineMapper.toEntity(airlineEntity));
                    return true;
                }));
        logger.info("airline: "+ seller + " sold aircraft: "+ aircraft + " to airline: " + buyer);
    }
}
