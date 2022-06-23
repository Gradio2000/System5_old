package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "q_questions_for_attempt")
@Getter
@Setter
public class QuestionsForAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "attempt_id")
    private Integer attemptId;

    @Column(name = "question_id")
    private Integer questionId;

    public QuestionsForAttempt(Integer attemptId, Integer questionId) {
        this.attemptId = attemptId;
        this.questionId = questionId;
    }

    public QuestionsForAttempt() {

    }
}