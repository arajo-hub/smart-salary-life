package com.judy.smartsalarylife.member.domain;

import com.judy.smartsalarylife.common.entity.BaseEntity;
import com.judy.smartsalarylife.salary.domain.Salary;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Salary> salarys;

}
