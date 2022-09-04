package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "q_appoint_test_amount")
@Getter
@Setter
public class AppointTestAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Integer appointId;

    @Column
    private Integer testId;

    @Column
    private Integer quesAmount;
}
