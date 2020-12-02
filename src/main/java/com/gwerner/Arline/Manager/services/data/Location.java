package com.gwerner.Arline.Manager.services.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private String Name;
    private Double latitude;
    private Double longitude;
}
