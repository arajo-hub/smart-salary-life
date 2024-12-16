package com.judy.smartsalarylife.salary.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class SalaryCreateRequestDto {

    @NotNull
    @PastOrPresent
    private LocalDate salaryDate;
    @Positive
    private int salary;
    @Valid
    @Size(min = 1, max = 100)
    private List<SalaryUsageInfoCreateRequestDto> salaryUsageInfos;

}
