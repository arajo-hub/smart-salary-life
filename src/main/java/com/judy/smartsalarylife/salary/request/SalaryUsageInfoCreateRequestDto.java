package com.judy.smartsalarylife.salary.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class SalaryUsageInfoCreateRequestDto {

    @NotBlank
    @Length(min = 1, max = 50)
    private String name;
    @Positive
    private int amount;

}
