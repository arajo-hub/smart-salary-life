package com.judy.smartsalarylife.salary.controller;

import com.judy.smartsalarylife.salary.request.SalaryCreateRequestDto;
import com.judy.smartsalarylife.salary.response.SalaryCreateResponseDto;
import com.judy.smartsalarylife.salary.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SalaryController {

    private final SalaryService salaryService;

    @PostMapping(value = "/salaries", produces = "application/json")
    public ResponseEntity<SalaryCreateResponseDto> createSalary(@RequestBody SalaryCreateRequestDto salaryCreateRequestDto) {
        return salaryService.createSalary(salaryCreateRequestDto);
    }

}
