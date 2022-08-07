package com.example.qtest.model;

import com.example.system5.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "q_appoint_tests")
@Getter
@Setter
//@Where(clause = "finished = false")
public class AppointTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appoint_test", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "finished")
    private Boolean finished;

    @Column
    private String base;

    @OneToOne
    @JoinColumn(name = "attempt_test_id", referencedColumnName = "attempt_id")
    private Attempttest attempttest;

    public AppointTest(User user, Test test, Boolean finished, String base) {
        this.user = user;
        this.test = test;
        this.finished = finished;
        this.base = base;
    }

    public AppointTest() {
    }
}