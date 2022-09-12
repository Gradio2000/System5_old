package com.example.system5.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sys_positions")
@Getter
@Setter
public class Position {
    @Column(name = "position_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int position_id;

    @Column(name = "position")
    private String position;

    @Column(name = "division_id")
    private int divisionId;

    @OneToOne
    @JoinTable(name = "sys_position_user",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonManagedReference
    public User user;



}
