package com.gwerner.Arline.Manager.services.repository;

import com.gwerner.Arline.Manager.services.data.Airline;
import com.gwerner.Arline.Manager.services.entities.AirlineEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends CrudRepository<AirlineEntity,String> {
}
