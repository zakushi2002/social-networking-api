package com.social.networking.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "db_wfa_0_to_5_years")
@Getter
@Setter
public class WeightForAge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer gender;
    @Column(name = "months")
    private Integer age;
    @Column(name = "l")
    private Double power;
    @Column(name = "m")
    private Double median;
    @Column(name = "s")
    private Double variation;
    private Double sd3neg;
    private Double sd2neg;
    private Double sd1neg;
    private Double sd0;
    private Double sd1;
    private Double sd2;
    private Double sd3;
}
