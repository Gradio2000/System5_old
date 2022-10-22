package com.example.kladr.service;

import com.example.dto.HouseDto;
import com.example.kladr.model.House;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class KladrService {
    public List<HouseDto> getHouseDtoList(List<House> houseList){
        List<HouseDto> houseDtoList = new ArrayList<>();
        int id = 1;
        for (House house: houseList){
            String[] houseMass = house.getName().split(",");
            for (String mass : houseMass) {
                HouseDto houseDto = HouseDto.getInstance(id, mass, house.getIndex());
                houseDtoList.add(houseDto);
                id++;
            }
        }
        return houseDtoList;
    }
}
