package com.gwerner.Arline.Manager.services;

import com.gwerner.Arline.Manager.services.data.Airline;
import com.gwerner.Arline.Manager.services.entities.AircraftEntity;
import com.gwerner.Arline.Manager.services.entities.AirlineEntity;
import com.gwerner.Arline.Manager.services.entities.LocationEntity;
import com.gwerner.Arline.Manager.services.mappers.AircraftMapper;
import com.gwerner.Arline.Manager.services.mappers.AirlineMapper;
import com.gwerner.Arline.Manager.services.repository.AircraftRepository;
import com.gwerner.Arline.Manager.services.repository.AirlineRepository;
import com.gwerner.Arline.Manager.services.repository.LocationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
class AirLineManagerServiceTest {


    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private AirlineEntity airline;

    @Mock
    private AirlineEntity airline2;

    @Mock
    private LocationEntity homebase2;

    @Mock
    private LocationEntity homebase;

    @Mock
    private AircraftEntity aircraftEntity1;

    private AirLineManagerService service;

    @BeforeEach
    private void init(){
        MockitoAnnotations.initMocks(this);
        when(homebase.getName()).thenReturn("home");
        when(homebase.getLatitude()).thenReturn(12345.1);
        when(homebase.getLongitude()).thenReturn(12345.1);
        when(airline.getBalance()).thenReturn(1000.0);
        when(airline.getName()).thenReturn("airline");
        when(airline.getHomeBase()).thenReturn(homebase);
        when(aircraftEntity1.getAirline()).thenReturn("airline");
        when(aircraftEntity1.getMaxRange()).thenReturn(1000);
        when(aircraftEntity1.getPrice()).thenReturn(100.0);
        when(aircraftEntity1.getName()).thenReturn("aircraft1");
        when(aircraftEntity1.getStartTime()).thenReturn(LocalDate.now());
        when(aircraftRepository.findById("aircraft1")).thenReturn(java.util.Optional.of(aircraftEntity1));
        when(airlineRepository.findById("airline")).thenReturn(java.util.Optional.of(airline));
        service = new AirLineManagerService(airlineRepository,aircraftRepository,locationRepository,airlineMapper,aircraftMapper,validationService);
    }


    private AirlineMapper airlineMapper = Mappers.getMapper(AirlineMapper.class);

    private AircraftMapper aircraftMapper = Mappers.getMapper(AircraftMapper.class);

    @Test
    void sellAirCraft() {
        service.sellAirCraft("airline","aircraft1");
        verify(aircraftRepository).deleteById("aircraft1");
        verify(airlineRepository).save(new AirlineEntity("airline",1100.0,new LocationEntity("home",12345.1,12345.1)));
    }

    @Test
    void sellAirCraftPriceUpdate() {
        when(aircraftEntity1.getStartTime()).thenReturn(LocalDate.now().minusMonths(2));
        service.sellAirCraft("airline","aircraft1");
        verify(aircraftRepository).deleteById("aircraft1");
        verify(airlineRepository).save(new AirlineEntity("airline",1096.0,new LocationEntity("home",12345.1,12345.1)));
    }


    @Test
    void getAirlineDistance() {
        when(airlineRepository.findById("airline")).thenReturn(java.util.Optional.of(airline));
        service.getAirlineDistance("airline");
    }

    @Test
    void getDestinations() {
    }

    @Test
    void buyAircraft() {
        when(aircraftEntity1.getStartTime()).thenReturn(LocalDate.now().minusMonths(2));
        when(homebase2.getName()).thenReturn("home2");
        when(homebase2.getLatitude()).thenReturn(43123314.1);
        when(homebase2.getLongitude()).thenReturn(4312322.1);
        when(airline2.getBalance()).thenReturn(1000.0);
        when(airline2.getName()).thenReturn("airline2");
        when(airline2.getHomeBase()).thenReturn(homebase2);
        when(airlineRepository.findById("airline2")).thenReturn(java.util.Optional.of(airline2));
        service.buyAircraft("airline2","airline","aircraft1");
        verify(aircraftRepository).deleteById("aircraft1");
        verify(aircraftRepository).save(new AircraftEntity(aircraftEntity1.getName(),aircraftEntity1.getPrice(),aircraftEntity1.getStartTime(),"airline2",aircraftEntity1.getMaxRange()));
        verify(airlineRepository).save(new AirlineEntity("airline",1096.0,new LocationEntity("home",12345.1,12345.1)));
        verify(airlineRepository).save(new AirlineEntity("airline2",904.0,new LocationEntity("home2",43123314.1,4312322.1)));
    }
}