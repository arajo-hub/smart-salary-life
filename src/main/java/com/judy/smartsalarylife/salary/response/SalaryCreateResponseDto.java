package com.judy.smartsalarylife.salary.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SalaryCreateResponseDto {

    private Long salaryId;
    private Long memberId;
    private LocalDate salaryDate;
    private int salary;
    private List<SalaryUsageInfoCreateResponseDto> salaryUsageInfos;

}
