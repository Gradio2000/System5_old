package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "q_questions")
@Getter
@Setter
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @Column(name = "question_name", nullable = false)
    private String questionName;

    @Column(name = "test_id")
    private Integer testId;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(targetEntity = Answer.class, cascade = CascadeType.ALL,
    fetch = FetchType.EAGER, mappedBy = "question")
    @OrderBy("id")

    private List<Answer> answers;


}