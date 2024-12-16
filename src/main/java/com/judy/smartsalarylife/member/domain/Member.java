package com.judy.smartsalarylife.member.domain;

import com.judy.smartsalarylife.common.entity.BaseEntity;
import com.judy.smartsalarylife.salary.domain.Salary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String encryptedPassword;

    @OneToMany(mappedBy = "member")
    private List<Salary> salarys;

}
