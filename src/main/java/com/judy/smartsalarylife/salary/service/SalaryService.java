package com.judy.smartsalarylife.salary.service;

import com.judy.smartsalarylife.common.exception.SmartSalaryLifeException;
import com.judy.smartsalarylife.common.exception.enums.ErrorCode;
import com.judy.smartsalarylife.salary.domain.Salary;
import com.judy.smartsalarylife.salary.domain.SalaryUsageInfo;
import com.judy.smartsalarylife.salary.repository.SalaryRepository;
import com.judy.smartsalarylife.salary.request.SalaryCreateRequestDto;
import com.judy.smartsalarylife.salary.request.SalaryUsageInfoCreateRequestDto;
import com.judy.smartsalarylife.salary.response.SalaryCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<SalaryCreateResponseDto> createSalary(SalaryCreateRequestDto salaryCreateRequestDto) {
        // 월급은 현재까지만 입력 가능
        if (salaryCreateRequestDto.getSalaryDate().isAfter(LocalDate.now())) {
            throw new SmartSalaryLifeException(ErrorCode.FUTURE_SALARY_DATE);
        }

        // 월급사용정보 길이 체크
        if (salaryCreateRequestDto.getSalaryUsageInfos().size() > 100) {
            throw new SmartSalaryLifeException(ErrorCode.INVALID_LENGTH_SALARY_USAGE_INFO);
        }

        // 월급사용정보 전체 금액 합이 월급과 일치하는지 확인
        int sum = salaryCreateRequestDto.getSalaryUsageInfos().stream()
                .mapToInt(SalaryUsageInfoCreateRequestDto::getAmount)
                .sum();

        if (salaryCreateRequestDto.getSalary() != sum) {
            throw new SmartSalaryLifeException(ErrorCode.INVALID_SALARY_USAGE_INFO_SUM);
        }

        List<SalaryUsageInfo> salaryUsageInfos = salaryCreateRequestDto.getSalaryUsageInfos().stream().map(info -> this.modelMapper.map(info, SalaryUsageInfo.class)).toList();
        Salary salary = this.modelMapper.map(salaryCreateRequestDto, Salary.class);
        salary.setSalaryUsageInfos(salaryUsageInfos);

        salaryRepository.save(salary);

        SalaryCreateResponseDto salaryCreateResponseDto = this.modelMapper.map(salary, SalaryCreateResponseDto.class);
        return ResponseEntity.ok(salaryCreateResponseDto);
    }
}
