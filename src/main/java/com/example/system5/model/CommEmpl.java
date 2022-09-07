package com.example.system5.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sys_com_empl")
@Getter
@Setter
public class CommEmpl {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "empl_id", referencedColumnName = "user_id")
    private User emplUser;

    @OneToOne
    @JoinColumn(name = "comm_id", referencedColumnName = "user_id")
    private User commUser;

    public CommEmpl(User emplUser, User commUser) {
        this.emplUser = emplUser;
        this.commUser = commUser;
    }

    public CommEmpl() {
    }
}
