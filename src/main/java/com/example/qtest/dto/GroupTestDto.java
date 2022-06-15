package com.example.qtest.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class GroupTestDto implements Serializable {
    private Integer grouptestId;
    @NotBlank
    private String name;

    public GroupTestDto(Integer grouptestId, String name) {
        this.grouptestId = grouptestId;
        this.name = name;
    }
}
