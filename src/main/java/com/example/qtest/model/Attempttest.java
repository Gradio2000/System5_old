package com.example.qtest.model;

import com.example.system5.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private Date dateTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

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

    @Column(name = "test_id")
    private Integer testId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "test_id", nullable = false, insertable = false, updatable = false)
    private Test test;
}