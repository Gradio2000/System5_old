package com.example.system5.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "total_mark5")
@Getter
@Setter
@Component
public class TotalMark5 {
    @Id
    private int system5IdTotalMark;

    @Column(name = "total_mark")
    private String totalMark;

    @Column(name = "total_markempl")
    private String totalMarkEmpl;

    @OneToOne
    @JoinColumn(name = "system5_id_total_mark")
    @MapsId
    private System5 system5;

}
