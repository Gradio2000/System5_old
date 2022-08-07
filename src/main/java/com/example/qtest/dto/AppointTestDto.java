package com.example.qtest.dto;

import com.example.system5.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointTestDto {
    private TestDto testDto;
    private UserDto userDto;
    private Boolean finished;
    private String base;
    private AttempttestDto attempttestDto;

    public AppointTestDto(TestDto testDto, UserDto userDto, Boolean finished, String base,
                          AttempttestDto attempttestDto) {
        this.testDto = testDto;
        this.userDto = userDto;
        this.finished = finished;
        this.base = base;
        this.attempttestDto = attempttestDto;
    }

    public AppointTestDto() {
    }
}
