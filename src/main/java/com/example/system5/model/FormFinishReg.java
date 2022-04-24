package com.example.system5.model;

public class FormFinishReg {
    private String name;
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
