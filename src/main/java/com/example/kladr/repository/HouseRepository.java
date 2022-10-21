package com.example.kladr.repository;

import com.example.kladr.model.House;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Integer> {

    List<House>
    findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCodeAndStreetCode(
            Integer regCode, Integer areaCode, Integer cityCode,
            Integer punktCode, Integer streetCode, PageRequest of);

    @Query(nativeQuery = true,
            value = "SELECT * FROM kl_house WHERE reg_code = :regCodeId " +
                    "AND area_code = :areaCodeId " +
                    "AND city_code = :cityCodeId " +
                    "AND punkt_code = :punktCodeId " +
                    "AND street_code = :streetCodeId AND name ~ :value")
    List<House> getHouses(Integer regCodeId, Integer areaCodeId,
                          Integer cityCodeId, Integer punktCodeId, Integer streetCodeId, String value);
}
