package com.judy.smartsalarylife.salary.domain;

import com.judy.smartsalarylife.common.entity.BaseEntity;
import com.judy.smartsalarylife.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Salary extends BaseEntity {

    @Id
    @Column(name = "SALARY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate salaryDate;

    private int salary;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "salary")
    private List<SalaryUsageInfo> salaryUsageInfos;

}
