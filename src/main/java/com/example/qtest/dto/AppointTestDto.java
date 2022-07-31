package com.example.qtest.dto;

import com.example.system5.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointTestDto {
    private TestDto testDto;
    private UserDto userDto;

    public AppointTestDto(TestDto testDto, UserDto userDto) {
        this.testDto = testDto;
        this.userDto = userDto;
    }
}
