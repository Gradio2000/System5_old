package com.example.system5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Shtat {
    @Id
    @Column(name = "shtat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int shtat_id;

    @OneToOne(targetEntity = Division.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "division_id", referencedColumnName = "division_id")
    private Division division;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    public int getShtat_id() {
        return shtat_id;
    }

    public void setShtat_id(int shtat_id) {
        this.shtat_id = shtat_id;
    }


    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
