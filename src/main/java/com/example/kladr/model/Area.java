package com.example.kladr.model;

import com.example.kladr.config.AreaId;
import lombok.Getter;
import lombok.Setter;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "reg_code", referencedColumnName = "reg_code"),
            @JoinColumn(name = "area_code", referencedColumnName = "area_code")
    })
    private List<City> cityList;
}
