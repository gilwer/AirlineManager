package com.gwerner.Arline.Manager.controller;

import com.gwerner.Arline.Manager.DTO.*;
import com.gwerner.Arline.Manager.services.AirLineManagerService;
import com.gwerner.Arline.Manager.services.data.AvailableDestinations;
import com.gwerner.Arline.Manager.services.mappers.AircraftMapper;
import com.gwerner.Arline.Manager.services.mappers.AirlineMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ArlineManagerController {

    private AirLineManagerService service;
    private AirlineMapper airlineMapper;
    private AircraftMapper aircraftMapper;

    @Autowired
    public ArlineManagerController(AirLineManagerService service, AirlineMapper airlineMapper, AircraftMapper aircraftMapper) {
        this.service = service;
        this.airlineMapper = airlineMapper;
        this.aircraftMapper = aircraftMapper;
    }

    @ApiOperation(value = "airline", nickname = "addAirline")
    @PostMapping(value = "/addAirline")
    @ResponseStatus(HttpStatus.OK)
    void addAirline(@RequestBody AirlineDTO airline){
        service.addAirline(airlineMapper.fromDto(airline));
    }

    @ApiOperation(value = "airlineBalance")
    @PostMapping(value = "/getBalance")
    @ResponseStatus(HttpStatus.OK)
    AirlineBalanceListDTO getAirlineBalance(){
        return airlineMapper.toDto(service.getAirlineBalance());
    }

    @ApiOperation(value = "aircraft", nickname = "addAircraft")
    @PostMapping(value = "/addAirCraft/{airline}")
    @ResponseStatus(HttpStatus.OK)
    void addAirCraft(@PathVariable(value = "airline") String airline,@RequestBody AircraftDTO aircraft){
        service.addAirCraft(airline,aircraftMapper.fromDto(aircraft));
    }

    @ApiOperation(value = "sell", nickname = "sellAircraft")
    @PutMapping(value = "/sellAirCraft/{airline}/{aircraft}")
    @ResponseStatus(HttpStatus.OK)
    void sellAirCraft(@PathVariable(value = "airline") String airline,@PathVariable(value = "aircraft") String aircraft){
        service.sellAirCraft(airline,aircraft);
    }

    @ApiOperation(value = "location", nickname = "addLocation")
    @PostMapping(value = "/addLocation")
    @ResponseStatus(HttpStatus.OK)
    void addLocation(@RequestBody LocationDTO locationDTO){
        service.addLocation(airlineMapper.fromDto(locationDTO));
    }

    @ApiOperation(value = "airlineDistance")
    @GetMapping(value = "/getDistance/{airline}")
    @ResponseStatus(HttpStatus.OK)
    AirlineDistanceListDTO getAirlineDistance(@PathVariable(value = "airline") String airline){return airlineMapper.toDto(service.getAirlineDistance(airline));}

    @ApiOperation(value = "airlineDestinations")
    @GetMapping(value = "/getDestinations/{airline}")
    @ResponseStatus(HttpStatus.OK)
    AvailableDestinationsDTO getAvailableDestinations(@PathVariable("airline") String airline){return airlineMapper.toDto(service.getDestinations(airline));}

    @ApiOperation(value = "buy", nickname = "buyAircraft")
    @PutMapping(value = "/buyAirCraft/{buyer}/{seller}/{aircraft}")
    @ResponseStatus(HttpStatus.OK)
    void buyAirCraft(@PathVariable(value = "buyer") String buyer, @PathVariable(value = "seller") String seller, @PathVariable(value = "aircraft") String aircraft){ service.buyAircraft(buyer,seller,aircraft);}

}
