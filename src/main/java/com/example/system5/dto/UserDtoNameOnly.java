package com.example.system5.dto;

import com.example.system5.model.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDtoNameOnly implements Serializable {
    private Integer userId;
    private String name;

    public UserDtoNameOnly(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public UserDtoNameOnly() {
    }

    public static UserDtoNameOnly getInstance(User user){
        return new UserDtoNameOnly(user.getUserId(), user.getName());
    }
}
