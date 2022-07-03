package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "q_attempttests")
@Getter
@Setter
public class Attempttest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attempt_id", nullable = false)
    private Integer id;

    @Column(name = "date_time", nullable = false)
    private Instant dateTime;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "time_attempt")
    private Integer timeAttempt;

    @Column(name = "amount_ques")
    private Integer amountQues;

    @Column(name = "amount_false_answers")
    private Integer amountFalseAnswers;

    @Column(name = "amount_true_answers")
    private Integer amountTrueAnswers;

    @Column(name = "result")
    private Double result;

    @Column(name = "testresult")
    private String testResult;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "test_id", nullable = false)
//    private Test test;

    @Column(name = "test_id")
    private Integer testId;

}