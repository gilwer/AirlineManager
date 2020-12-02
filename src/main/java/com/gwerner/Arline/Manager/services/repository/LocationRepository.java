package com.gwerner.Arline.Manager.services.repository;

import com.gwerner.Arline.Manager.services.entities.AirlineEntity;
import com.gwerner.Arline.Manager.services.entities.LocationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<LocationEntity,String> {
    List<LocationEntity> findByNameOrLatitudeAndLongitude(String name,Double latitude,Double longitude);
}
