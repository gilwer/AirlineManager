package com.gwerner.Arline.Manager.services.repository;

import com.gwerner.Arline.Manager.services.data.Aircraft;
import com.gwerner.Arline.Manager.services.entities.AircraftEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends CrudRepository<AircraftEntity,String> {

    List<AircraftEntity> findByAirline(String airline);
}
