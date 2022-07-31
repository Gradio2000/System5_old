package com.example.qtest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GroupTestDto implements Serializable {
    private Integer grouptestId;
    @NotBlank
    private String name;
    List<TestDto> testDtoList;

    public GroupTestDto(Integer grouptestId, String name) {
        this.grouptestId = grouptestId;
        this.name = name;
    }

    public GroupTestDto(Integer grouptestId, String name, List<TestDto> testDtoList) {
        this.grouptestId = grouptestId;
        this.name = name;
        this.testDtoList = testDtoList;
    }
}
