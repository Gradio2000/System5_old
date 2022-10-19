package com.example.kladr.repository;

import com.example.kladr.model.Street;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StreetRepository extends JpaRepository<Street, Integer> {

    List<Street> findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCode(int regCode, int areaCode, int cityCode, int punktCode, PageRequest of);


    @Query(nativeQuery = true,
            value = "SELECT * FROM kl_street WHERE reg_code = :regCodeId " +
                    "AND area_code = :areaCodeId " +
                    "AND city_code = :cityCodeId " +
                    "AND punkt_code = :punktCodeId AND name ~ :value")
    List<Street> getStreet(Integer regCodeId, Integer areaCodeId, Integer cityCodeId, Integer punktCodeId, String value);
}
