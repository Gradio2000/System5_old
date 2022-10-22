package com.example.kladr.model;


import com.example.kladr.config.CityId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kl_city")
@Getter
@Setter
@IdClass(CityId.class)
public class City {

    @Id
    @Column(name = "reg_code")
    private Integer regCode;

    @Id
    @Column(name = "area_code")
    private Integer areaCode;

    @Id
    @Column(name = "city_code")
    private Integer cityCode;

    @Column
    private String name;

    @Column
    private String socr;

    @OneToMany(targetEntity = Street.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumns({
            @JoinColumn(name = "reg_code", referencedColumnName = "reg_code"),
            @JoinColumn(name = "area_code", referencedColumnName = "area_code"),
            @JoinColumn(name = "city_code", referencedColumnName = "city_code")
    })
    private List<Street> streetList;



}
