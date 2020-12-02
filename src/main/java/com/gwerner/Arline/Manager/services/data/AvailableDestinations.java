package com.gwerner.Arline.Manager.services.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDestinations {
    List<String> destinations;
}
