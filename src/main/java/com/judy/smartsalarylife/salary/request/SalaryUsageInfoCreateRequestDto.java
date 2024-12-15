package com.judy.smartsalarylife.salary.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SalaryUsageInfoCreateRequestDto {

    private String content;
    private int amount;

}
