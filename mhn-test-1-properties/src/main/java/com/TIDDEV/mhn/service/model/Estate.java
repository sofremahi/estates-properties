package com.TIDDEV.mhn.service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "TB_ESTATE")
@Entity
public class Estate {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PROVINCE_CODE", nullable = false)
    @JsonBackReference("province-estate")
    private Province province;
    @ManyToOne
    @JoinColumn(name = "CITY_CODE", nullable = false)
    @JsonBackReference("city-estate")
    private City city;
    @ManyToOne
    @JoinColumn(name = "ZONE_CODE", nullable = false)
    @JsonBackReference("zone-estate")
    private Zone zone;
    private LocalDate dateYear;
    @Column(name = "PRICE_PER_SQUARE_METER", nullable = false)
    private BigDecimal pricePerSquareMeter;
}
