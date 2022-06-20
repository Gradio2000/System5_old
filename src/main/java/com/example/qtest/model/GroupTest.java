package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "q_group_test")
@Getter
@Setter
public class GroupTest {
    @Id
    @Column(name = "grouptest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer grouptestId;

    @Column(name = "name")
    @NotBlank
    private String name;

    @OneToMany(targetEntity = Test.class, cascade = CascadeType.ALL,
    fetch = FetchType.EAGER, mappedBy = "groupId")
    @OrderBy("testId")
    private List<Test> tests;
}
