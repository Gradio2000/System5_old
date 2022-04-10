package com.example.system5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Table(name = "system5")
public class System5 extends RepresentationModel<System5> {
    @Id
    @Column(name = "system5_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int system5Id;

//    @Column(name = "month_id")
//    private int monthId;

    private String monthName;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    @Column(name = "res1")
    private String res1;
    @Column(name = "res2")
    private String res2;
    @Column(name = "res3")
    private String res3;
    @Column(name = "res4")
    private String res4;
    @Column(name = "res5")
    private String res5;

    public int getSystem5Id() {
        return system5Id;
    }

    public void setSystem5Id(int system5Id) {
        this.system5Id = system5Id;
    }

//    public int getMonthId() {
//        return monthId;
//    }
//
//    public void setMonthId(int monthId) {
//        this.monthId = monthId;
//    }

    public String getRes1() {
        return res1;
    }

    public void setRes1(String res1) {
        this.res1 = res1;
    }

    public String getRes2() {
        return res2;
    }

    public void setRes2(String res2) {
        this.res2 = res2;
    }

    public String getRes3() {
        return res3;
    }

    public void setRes3(String res3) {
        this.res3 = res3;
    }

    public String getRes4() {
        return res4;
    }

    public void setRes4(String res4) {
        this.res4 = res4;
    }

    public String getRes5() {
        return res5;
    }

    public void setRes5(String res5) {
        this.res5 = res5;
    }
}
