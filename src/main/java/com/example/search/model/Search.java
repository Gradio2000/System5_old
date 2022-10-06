package com.example.search.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pindx17")
@Getter
@Setter
public class Search {
    @Id
    @Column
    private Integer id;

    @Column
    private String opsname;
}
