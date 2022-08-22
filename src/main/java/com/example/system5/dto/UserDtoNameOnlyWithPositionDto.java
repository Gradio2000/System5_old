package com.example.system5.dto;

import com.example.system5.model.User;
import lombok.Data;

@Data
public class UserDtoNameOnlyWithPositionDto {
    private Integer userId;
    private String name;
    private PositionDtoNameOnly positionDtoNameOnly;

    public UserDtoNameOnlyWithPositionDto(Integer userId, String name, PositionDtoNameOnly positionDtoNameOnly) {
        this.userId = userId;
        this.name = name;
        this.positionDtoNameOnly = positionDtoNameOnly;
    }

    public UserDtoNameOnlyWithPositionDto() {
    }

    public static UserDtoNameOnlyWithPositionDto getInstance(User user){
        return new UserDtoNameOnlyWithPositionDto(user.getUserId(), user.getName(),
                PositionDtoNameOnly.getInstance(user.getPosition()));
    }
}
