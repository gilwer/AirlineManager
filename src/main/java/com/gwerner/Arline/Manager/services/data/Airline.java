package com.gwerner.Arline.Manager.services.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Airline {
    private Double balance;
    private String name;
    private Location homeBase;

}
