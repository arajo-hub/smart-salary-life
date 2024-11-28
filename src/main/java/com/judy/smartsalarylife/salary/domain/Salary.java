package com.judy.smartsalarylife.salary.domain;

import com.judy.smartsalarylife.common.entity.BaseEntity;
import com.judy.smartsalarylife.member.domain.Member;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Salary extends BaseEntity {

    @Id
    @Column(name = "SALARY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate salaryDate;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "salary")
    private List<SalaryUsageInfo> SalaryUsageInfos;

}
