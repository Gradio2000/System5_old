package com.example.kladr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kl_region")
@Getter
@Setter
public class Region {
    @Id
    @Column(name = "reg_code")
    private Integer regCode;

    @Column
    private String name;

    @Column
    private String socr;

    @OneToMany(targetEntity = Area.class, cascade = CascadeType.ALL,
    fetch = FetchType.EAGER, mappedBy = "regCode")
    private List<Area> areaList;
}
