package com.gwerner.Arline.Manager.services.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirlineEntity {
    @Id
    private String name;
    private Double balance;
    private LocationEntity homeBase;
}
