package com.example.system5.dto;

import com.example.system5.model.User;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    @Size(max = 128, message = "size is too much")
    private final String name;

    public static UserDto getInstance(User user){
        return new UserDto(user.getName());
    }
}
