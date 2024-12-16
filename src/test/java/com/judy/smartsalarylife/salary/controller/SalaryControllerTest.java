package com.judy.smartsalarylife.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judy.smartsalarylife.salary.request.SalaryCreateRequestDto;
import com.judy.smartsalarylife.salary.request.SalaryUsageInfoCreateRequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class SalaryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("월급 생성")
    void createSalary() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(1_000_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();
        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("월급사용정보의 합이 월급과 일치하지 않음")
    void notEqualsSalaryToSumOfSalaryUsagesInfos() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(1_000_001).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("미래 월급 입력 불가능")
    void futureSalaryDate() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(1_000_001).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.of(2099, 1, 1)).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("월급 사용 정보 입력 길이 초과")
    void invalidLengthSalaryUsageInfos() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(10_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            usageInfosList.add(usageInfo);
        }
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(1_000_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("월급사용정보 금액은 양의 정수만 가능")
    void invalidAmountSalaryUsageInfo() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(-10_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(-10_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("월급 금액은 양의 정수만 가능")
    void invalidSalary() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("저축").amount(10_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(-10_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("월급사용정보 이름은 빈 값 입력 불가능")
    void notBalnkNameSalaryUsageInfo() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("       ").amount(10_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(10_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("월급사용정보 이름은 1 ~ 50자까지 가능")
    void overSizeNameSalaryUsageInfo() throws Exception {
        SalaryUsageInfoCreateRequestDto usageInfo = SalaryUsageInfoCreateRequestDto.builder().name("한국어 문장을 정확히 오십일 글자로 작성하는 것은 꽤 까다롭습니다. 한국어 문장을 정확히 오십일 글자로 작성하는 것은 꽤 까다롭습니다.").amount(10_000).build();
        List<SalaryUsageInfoCreateRequestDto> usageInfosList = List.of(usageInfo);
        SalaryCreateRequestDto requestDto = SalaryCreateRequestDto.builder().salaryDate(LocalDate.now()).salary(10_000).salaryUsageInfos(usageInfosList).build();

        String requestBody = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/salaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}
