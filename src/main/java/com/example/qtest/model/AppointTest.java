package com.example.qtest.model;

import com.example.system5.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "q_appoint")
@Getter
@Setter
public class AppointTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appoint_test", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "finished")
    private Boolean finished;

    @Column
    private String base;

    @Column
    private Boolean eko;

    @Column
    private Integer amountQues;

    @Column
    private String testName;

    @OneToOne
    @JoinColumn(name = "attempt_test_id", referencedColumnName = "attempt_id")
    private Attempttest attempttest;

}