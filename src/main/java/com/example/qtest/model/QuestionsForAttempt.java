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

    @OneToOne(targetEntity = Question.class)
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    private Question question;

    public QuestionsForAttempt() {
    }

    public QuestionsForAttempt(Integer attemptId, Question question) {
        this.attemptId = attemptId;
        this.question = question;
    }
}