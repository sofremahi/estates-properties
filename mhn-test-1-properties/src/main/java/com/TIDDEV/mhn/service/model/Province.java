package com.TIDDEV.mhn.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "TB_PROVINCE")
@Entity
public class Province {
    @Id
    @Column(name = "code", nullable = false)
    private Long code;
    @Column(name = "title", nullable = false)
    private String title;

}

