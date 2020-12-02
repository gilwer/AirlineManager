package com.gwerner.Arline.Manager.services.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AircraftEntity {
    @Id
    private String name;
    private Double price;
    private LocalDate startTime;
    private String airline;
    private Integer maxRange;
    @Version
    private Integer version;
}
