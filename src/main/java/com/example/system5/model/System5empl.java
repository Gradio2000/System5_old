package com.example.system5.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "sys_system5empl")
@Getter
@Setter
public class System5empl {

    @Id
    @Column(name = "system5_id")
    private int system5Id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "resempl1")
    @Pattern(regexp = "[A-Ea-e]")
    private String resempl1;

    @Column(name = "resempl2")
    @Pattern(regexp = "[A-Ea-e]")
    private String resempl2;

    @Column(name = "resempl3")
    @Pattern(regexp = "[A-Ea-e]")
    private String resempl3;

    @Column(name = "resempl4")
    @Pattern(regexp = "[A-Ea-e]")
    private String resempl4;

    @Column(name = "resempl5")
    @Pattern(regexp = "[A-Ea-e]")
    private String resempl5;


    @OneToOne
    @JoinColumn(name = "system5_id")
    @MapsId
    private System5 system5;


    public void setResempl1(String resempl1) {
        this.resempl1 = resempl1.toUpperCase();
    }

    public void setResempl2(String resempl2) {
        this.resempl2 = resempl2.toUpperCase();
    }

    public void setResempl3(String resempl3) {
        this.resempl3 = resempl3.toUpperCase();
    }

    public void setResempl4(String resempl4) {
        this.resempl4 = resempl4.toUpperCase();
    }

    public void setResempl5(String resempl5) {
        this.resempl5 = resempl5.toUpperCase();
    }
}
