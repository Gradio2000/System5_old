package com.example.system5.model;

import javax.validation.constraints.NotEmpty;

public class FormFinishReg {
    @NotEmpty(message = "заполните поле")
    private String name;

    @NotEmpty(message = "заполните поле")
    private int position_id;

    public FormFinishReg(String name, int position_id) {
        this.name = name;
        this.position_id = position_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
}
