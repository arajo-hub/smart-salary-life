package com.judy.smartsalarylife.salary.service;

import com.judy.smartsalarylife.common.exception.SmartSalaryLifeException;
import com.judy.smartsalarylife.common.exception.enums.ErrorCode;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(1_000_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        this.salaryService.createSalary(requestDto);

        List<Salary> salaries = this.salaryRepository.findAll();
        assertEquals(1, salaries.size());
        assertEquals(requestDto.getSalaryDate(), salaries.get(0).getSalaryDate());
        assertEquals(requestDto.getSalary(), salaries.get(0).getSalary());
        assertEquals(usageInfo.getName(), salaries.get(0).getSalaryUsageInfos().get(0).getContent());
        assertEquals(usageInfo.getAmount(), salaries.get(0).getSalaryUsageInfos().get(0).getAmount());
    }

    @Test
    @DisplayName("월급사용정보의 합이 월급과 일치하지 않음")
    void notEqualsSalaryToSumOfSalaryUsagesInfos() {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(1_000_001).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        SmartSalaryLifeException exception = assertThrows(SmartSalaryLifeException.class, () -> this.salaryService.createSalary(requestDto));
        assertEquals(ErrorCode.INVALID_SALARY_USAGE_INFO_SUM, exception.getErrorCode());
    }

    @Test
    @DisplayName("미래 월급 입력 불가능")
    void futureSalaryDate() {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(1_000_001).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.of(2099, 1, 1)).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        SmartSalaryLifeException exception = assertThrows(SmartSalaryLifeException.class, () -> this.salaryService.createSalary(requestDto));
        assertEquals(ErrorCode.FUTURE_SALARY_DATE, exception.getErrorCode());
    }

    @Test
    @DisplayName("월급 사용 정보 입력 길이 초과")
    void invalidLengthSalaryUsageInfos() {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(10_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            usageInfosList.add(usageInfo);
        }
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        SmartSalaryLifeException exception = assertThrows(SmartSalaryLifeException.class, () -> this.salaryService.createSalary(requestDto));
        assertEquals(ErrorCode.INVALID_LENGTH_SALARY_USAGE_INFO, exception.getErrorCode());
    }
}