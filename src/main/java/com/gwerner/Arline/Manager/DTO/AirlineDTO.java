package com.gwerner.Arline.Manager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AirlineDTO {
    private Double budget;
    private String name;
    private LocationDTO homeBase;
}
