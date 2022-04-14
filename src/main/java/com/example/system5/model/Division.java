package com.example.system5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "divisions")
public class Division {
    @Id
    @Column(name = "division_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int divisionId;

    @Basic
    @Column(name = "division")
    private String division;


    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Division division = (Division) o;

        if (divisionId != division.divisionId) return false;
        if (this.division != null ? !this.division.equals(division.division) : division.division != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = divisionId;
        result = 31 * result + (division != null ? division.hashCode() : 0);
        return result;
    }
}
