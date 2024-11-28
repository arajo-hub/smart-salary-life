package com.judy.smartsalarylife.salary.domain;

import com.judy.smartsalarylife.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
public class SalaryUsageInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SALARYUSAGEINFO_ID")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "SALARY_ID")
    private Salary salary;

}
