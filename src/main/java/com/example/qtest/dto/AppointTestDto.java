package com.example.qtest.dto;

import com.example.qtest.model.AppointTest;
import com.example.system5.dto.UserDtoNameOnlyWithPositionDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointTestDto {
    private UserDtoNameOnlyWithPositionDto userDtoNameOnlyWithPositionDto;
    private Boolean finished;
    private String base;
    private boolean eko;
    private Integer amountQues;
    private String testName;
    private Integer criteria;
    private AttempttestDto attempttestDto;

    public AppointTestDto(UserDtoNameOnlyWithPositionDto userDtoNameOnlyWithPositionDto,
                          Boolean finished, String base, boolean eko, Integer amountQues,
                          String testName, Integer criteria, AttempttestDto attempttestDto) {
        this.userDtoNameOnlyWithPositionDto = userDtoNameOnlyWithPositionDto;
        this.finished = finished;
        this.base = base;
        this.eko = eko;
        this.amountQues = amountQues;
        this.testName = testName;
        this.criteria = criteria;
        this.attempttestDto = attempttestDto;
    }

    public AppointTestDto() {
    }

    public static AppointTestDto getInstance(AppointTest appointTest){
        return new AppointTestDto(UserDtoNameOnlyWithPositionDto.getInstance(appointTest.getUser()),
                appointTest.getFinished(), appointTest.getBase(), appointTest.getEko(),
                appointTest.getAmountQues(), appointTest.getTestName(), appointTest.getCriteria(),
                AttempttestDto.getInstance(appointTest.getAttempttest()));
    }
}
