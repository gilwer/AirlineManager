package com.gwerner.Arline.Manager.services.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LocationEntity {
    @Id
    private String name;
    private Double latitude;
    private Double longitude;
    @Version
    private Integer version;
}
