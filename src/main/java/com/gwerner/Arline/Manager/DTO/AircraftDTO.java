package com.gwerner.Arline.Manager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftDTO {
    private String Name;
    private Double price;
    private Integer maxRange;
}
