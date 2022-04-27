package com.example.system5.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
public class MyForm {

    @NotEmpty(message = "Заполните поле")
    private String login;

    @NotEmpty(message = "Заполните поле")
    private String password;

    @NotEmpty(message = "Заполните поле")
    private String confpass;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfpass() {
        return confpass;
    }

    public void setConfpass(String confpass) {
        this.confpass = confpass;
    }
}
