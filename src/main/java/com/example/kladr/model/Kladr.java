package com.example.kladr.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kladr_kladr")
@Getter
@Setter
public class Kladr {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column
    private String name;

    @Column
    private String socr;

    @Column
    private Long code;

}
