package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "q_tests")
@Getter
@Setter
public class Test {
    @Id
    @Column(name = "test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer testId;

    @Column(name = "test_name")
    private String testName;

    @Column
    private Double criteria;

    @Column
    private Double time;

    @Column(name = "ques_amount")
    private Integer quesAmount;

    @Column
    private boolean deleted;

    @Column(name = "ques_mix")
    private boolean quesMix;

    @Column(name = "group_id")
    private Integer groupId;

    @OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL,
    fetch = FetchType.EAGER, mappedBy = "testId")
    private List<Question> questions;

}
