package com.example.system5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "divisions")
@Getter
@Setter
public class Division {
    @Id
    @Column(name = "division_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int divisionId;

    @Basic
    @Column(name = "division")
    private String division;

    @OneToMany(targetEntity = Position.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "division_id", referencedColumnName = "division_id")
    private List<Position> positions;


}
