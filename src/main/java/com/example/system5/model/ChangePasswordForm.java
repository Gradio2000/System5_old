package com.example.system5.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class ChangePasswordForm {
    @NotEmpty(message = "Заполните поле")
    private String password;

    @NotEmpty(message = "Заполните поле")
    private String confirmPassword;
}
