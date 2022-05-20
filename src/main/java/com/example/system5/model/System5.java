package com.example.system5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "system5")
@Getter
@Setter
public class System5 extends RepresentationModel<System5> {
    @Id
    @Column(name = "system5_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int system5Id;

    @Column(name = "user_id")
    @JsonIgnore
    private int userId;

    @Column(name = "month")
    private String month;

    @Column(name = "res1")
    @Pattern(regexp = "[A-Ea-e]")
    private String res1;

    @Column(name = "res2")
    @Pattern(regexp = "[A-Ea-e]")
    private String res2;

    @Column(name = "res3")
    @Pattern(regexp = "[A-Ea-e]")
    private String res3;

    @Column(name = "res4")
    @Pattern(regexp = "[A-Ea-e]")
    private String res4;

    @Column(name = "res5")
    @Pattern(regexp = "[A-Ea-e]")
    private String res5;

    @Column(name = "rated")
    private int rated;

    @OneToOne(mappedBy = "system5", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private System5empl system5empl;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToOne(mappedBy = "system5", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private TotalMark5 totalMark5;
}
