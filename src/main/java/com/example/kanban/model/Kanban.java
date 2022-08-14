package com.example.kanban.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "kan_kanban")
@Getter
@Setter
public class Kanban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kanban_id", nullable = false)
    private Integer id;

    @Column(name = "kanban_name")
    private String kanbanName;

    @Column(name = "started")
    private Boolean started;

    @Column(name = "continues")
    private Boolean continues;

    @Column(name = "finished")
    private Boolean finished;

}