package com.judy.smartsalarylife.salary.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class SalaryCreateRequestDto {

    private LocalDate salaryDate;
    private int salary;
    private List<SalaryUsageInfoCreateRequestDto> salaryUsageInfos;

}
