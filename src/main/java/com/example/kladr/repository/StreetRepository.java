package com.example.kladr.repository;

import com.example.kladr.model.Street;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StreetRepository extends JpaRepository<Street, Integer> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM kl_street WHERE reg_code = :val1 AND area_code = :val2 AND city_code = :val3 AND punkt_code = :val4 LIMIT 10")
    List<Street> getStreet(int val1, int val2, int val3, int val4);

    List<Street> findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCode(int regCode, int areaCode, int cityCode, int punktCode, PageRequest of);
}
