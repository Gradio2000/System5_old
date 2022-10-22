package com.example.kladr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDto {
    Integer id;
    private String name;
    private String index;

    public HouseDto(Integer id, String name, String index) {
        this.id = id;
        this.name = name;
        this.index = index;
    }

    public static HouseDto getInstance(Integer id, String name, String index){
        return new HouseDto(id, name, index);
    }
}
