package com.example.kladr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kl_punkt")
@Getter
@Setter
public class Punkt {
    @Id
    @Column
    private Integer id;

    @Column(name = "reg_code")
    private Integer regCode;

    @Column(name = "area_code")
    private String areaCode;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "punkt_code")
    private String punktCode;

    @Column
    private String name;

    @Column
    private String socr;
}
