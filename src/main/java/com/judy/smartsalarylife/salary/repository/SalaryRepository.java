package com.judy.smartsalarylife.salary.repository;

import com.judy.smartsalarylife.salary.domain.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
