package com.project.myproject.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;
    private double value;

    @Column(precision=2, scale=1)
    private BigDecimal percentFromReferralsOf1stLevelFromTasks;
    @Column(precision=2, scale=1)
    private BigDecimal percentFromReferralsOf1stLevel;
    @Column(precision=2, scale=1)
    private BigDecimal percentFromReferralsOf2stLevelFromTasks;
    @Column(precision=2, scale=1)
    private BigDecimal percentFromReferralsOf2stLevel;
    @Column(precision=2, scale=1)
    private BigDecimal percentFromAdvertiserReferrals;

    private int maximumAmountToWithdraw;

}
