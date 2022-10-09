package com.example.kladr.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "kl_city")
@Getter
@Setter
public class City {

    @Id
    @Column
    private Integer id;

    @Column(name = "reg_code")
    private Integer regCode;

    @Column(name = "area_code")
    private Integer areaCode;

    @Column(name = "city_code")
    private String cityCode;

    @Column
    private String name;

    @Column
    private String socr;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumns({
//            @JoinColumn(name = "reg_code", insertable = false, updatable = false),
//            @JoinColumn(name = "area_code", insertable = false, updatable = false) })
//    private Area area;
}
