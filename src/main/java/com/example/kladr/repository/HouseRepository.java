package com.example.kladr.repository;

import com.example.kladr.model.House;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Integer> {

    List<House>
    findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCodeAndStreetCode(
            Integer regCode, Integer areaCode, Integer cityCode,
            Integer punktCode, Integer streetCode, PageRequest of);
}
