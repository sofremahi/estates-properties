package com.TIDDEV.mhn.service.modelDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderPriceForZones {
    private Long zoneCode;
    private String zoneTitle;
    private LocalDate dateYear;
    private Double averagePrice;

    public OrderPriceForZones(Long zoneCode, String zoneTitle, LocalDate dateYear, Double averagePrice) {
        this.zoneCode = zoneCode;
        this.zoneTitle = zoneTitle;
        this.dateYear = dateYear;
        this.averagePrice = averagePrice;
    }

}