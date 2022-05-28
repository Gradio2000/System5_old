package com.example.system5.dto;

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

    public UserDto(String name, Set<Role> roles) {
        this.name = name;
        this.roles = roles;
    }

    public static UserDto getInstance(User user){
        return new UserDto(user.getName(), user.getRoles());
    }
}
