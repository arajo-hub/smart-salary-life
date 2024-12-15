package com.judy.smartsalarylife.salary.service;

import com.judy.smartsalarylife.salary.domain.Salary;
import com.judy.smartsalarylife.salary.domain.SalaryUsageInfo;
import com.judy.smartsalarylife.salary.repository.SalaryRepository;
import com.judy.smartsalarylife.salary.request.SalaryCreateRequestDto;
import com.judy.smartsalarylife.salary.response.SalaryCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<SalaryCreateResponseDto> createSalary(SalaryCreateRequestDto salaryCreateRequestDto) {
        List<SalaryUsageInfo> salaryUsageInfos = salaryCreateRequestDto.getSalaryUsageInfos().stream().map(info -> this.modelMapper.map(info, SalaryUsageInfo.class)).toList();
        Salary salary = this.modelMapper.map(salaryCreateRequestDto, Salary.class);
        salary.setSalaryUsageInfos(salaryUsageInfos);

        salaryRepository.save(salary);

        SalaryCreateResponseDto salaryCreateResponseDto = this.modelMapper.map(salary, SalaryCreateResponseDto.class);
        return ResponseEntity.ok(salaryCreateResponseDto);
    }
}
