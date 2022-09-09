package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

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
    private Boolean deleted;

    @Column(name = "group_id")
    private Integer groupId;

    @Column
    private Boolean used;

    @OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL,
    fetch = FetchType.EAGER, mappedBy = "testId")
    @OrderBy("id")
    @Where(clause = "deleted = false")
    private Set<Question> questions;

}
