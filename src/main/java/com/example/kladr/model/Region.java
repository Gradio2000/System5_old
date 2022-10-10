package com.example.kladr.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Where;

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
    mappedBy = "regCode")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Area> areaList;

    @OneToMany(targetEntity = City.class, mappedBy = "regCode")
    @Where(clause = "area_code = 0")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<City> cityList;

    @OneToMany(targetEntity = Punkt.class, mappedBy = "regCode")
    @Where(clause = "area_code = 0 AND city_code = 0")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Punkt> punktList;
}
