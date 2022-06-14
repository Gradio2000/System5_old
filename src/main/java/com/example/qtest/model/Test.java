package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "q_tests")
@Getter
@Setter
public class Test {
    @Id
    @Column(name = "test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;
    @Column
    private String testName;
    @Column
    private Double criteria;
    @Column
    private Double time;
    @Column
    private Integer ques_amount;
    @Column
    private boolean deleted;
    @Column
    private boolean ques_mix;

    @Column(name = "group_id")
    private Integer groupId;
}
