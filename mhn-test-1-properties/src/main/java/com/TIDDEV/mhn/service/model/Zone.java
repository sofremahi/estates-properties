package com.TIDDEV.mhn.service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Table(name = "TB_ZONE")
@Entity
public class Zone {
    @Id
    @Column(name = "code",nullable = false)
    private Long code;
    @Column(name = "title",nullable = false)
    private String title ;
    @ManyToOne
    @JoinColumn(name = "CITY_CODE",nullable = false)
    @JsonBackReference
    private City city ;

}
