package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "false_users_answers")
@Getter
@Setter
public class FalseUsersAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "attempt_id")
    private Integer attemptId;

}