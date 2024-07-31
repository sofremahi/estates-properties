package com.TIDDEV.mhn.service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "TB_CITY")
@Entity
public class City {
    @Id
    @Column(name = "code",nullable = false)
    private Long code;
    @Column(name = "title",nullable = false)
    private String title ;
    @ManyToOne
    @JoinColumn(name = "PROVINCE_CODE")
    @JsonBackReference
    private Province province ;


}
