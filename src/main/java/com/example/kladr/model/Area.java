package com.example.kladr.model;

import com.example.kladr.config.AreaId;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "kl_area")
@Getter
@Setter
@IdClass(AreaId.class)
public class Area {

    @Id
    @Column(name = "reg_code")
    private Integer regCode;

    @Id
    @Column(name = "area_code")
    private Integer areaCode;

    @Column
    private String name;

    @Column
    private String socr;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "reg_code", referencedColumnName = "reg_code"),
            @JoinColumn(name = "area_code", referencedColumnName = "area_code")
    })
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<City> cityList;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumns({
            @JoinColumn(name = "reg_code", referencedColumnName = "reg_code"),
            @JoinColumn(name = "area_code", referencedColumnName = "area_code")
    })
    private List<Punkt> punktList;
}
