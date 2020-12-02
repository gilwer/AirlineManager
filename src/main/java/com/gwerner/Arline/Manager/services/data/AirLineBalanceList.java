package com.gwerner.Arline.Manager.services.data;

import com.gwerner.Arline.Manager.DTO.AirlineBalanceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirLineBalanceList {
    List<AirlineBalance> airlines;
}
