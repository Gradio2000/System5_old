package com.example.qtest.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "q_group_test")
@Getter
@Setter
public class GroupTest {
    @Id
    @Column(name = "grouptest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer grouptest_id;

    @Column(name = "name")
    @NotBlank
    private String name;
}
