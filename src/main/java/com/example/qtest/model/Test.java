package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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
    private Boolean deleted;

    @Column(name = "ques_mix", columnDefinition = "boolean default false")
    private Boolean quesMix;

    @Column(name = "group_id")
    private Integer groupId;

    @OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL,
    fetch = FetchType.EAGER, mappedBy = "testId")
    @OrderBy("id")
    private Set<Question> questions;

}
