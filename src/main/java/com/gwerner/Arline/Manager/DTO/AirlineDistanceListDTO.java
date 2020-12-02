package com.gwerner.Arline.Manager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirlineDistanceListDTO {
    List<LocationDistanceDTO> distances;
}
