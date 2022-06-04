package com.example.system5.dto;

import com.example.system5.model.Position;
import com.example.system5.model.Role;
import com.example.system5.model.User;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    @Size(max = 128, message = "size is too much")
    private final String name;
    private  Set<Role> roles;
    private String login;
    private Position position;

    public UserDto(String name, Set<Role> roles, String login, Position position) {
        this.name = name;
        this.roles = roles;
        this.login = login;
        this.position = position;
    }

    public static UserDto getInstance(User user){
        return new UserDto(user.getName(), user.getRoles(), user.getLogin(), user.getPosition());
    }
}
