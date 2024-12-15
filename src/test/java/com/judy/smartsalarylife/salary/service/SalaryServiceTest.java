package com.judy.smartsalarylife.salary.service;

import com.judy.smartsalarylife.salary.domain.Salary;
import com.judy.smartsalarylife.salary.repository.SalaryRepository;
import com.judy.smartsalarylife.salary.request.SalaryCreateRequestDto;
import com.judy.smartsalarylife.salary.request.SalaryUsageInfoCreateRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class SalaryServiceTest {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryRepository salaryRepository;

    @Test
    @DisplayName("월급 생성")
    void createSalary() {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().content("저축").amount(1_000_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        this.salaryService.createSalary(requestDto);

        List<Salary> salaries = this.salaryRepository.findAll();
        assertEquals(1, salaries.size());
        assertEquals(requestDto.getSalaryDate(), salaries.get(0).getSalaryDate());
        assertEquals(requestDto.getSalary(), salaries.get(0).getSalary());
        assertEquals(usageInfo.getContent(), salaries.get(0).getSalaryUsageInfos().get(0).getContent());
        assertEquals(usageInfo.getAmount(), salaries.get(0).getSalaryUsageInfos().get(0).getAmount());
    }
}