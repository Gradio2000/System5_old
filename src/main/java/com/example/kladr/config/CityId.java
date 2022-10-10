package com.example.kladr.config;

import java.io.Serializable;
import java.util.Objects;

public class CityId implements Serializable {
    private Integer regCode;
    private Integer areaCode;
    private Integer cityCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityId cityId = (CityId) o;
        return Objects.equals(regCode, cityId.regCode) && Objects.equals(areaCode, cityId.areaCode) && Objects.equals(cityCode, cityId.cityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regCode, areaCode, cityCode);
    }

    public Integer getRegCode() {
        return regCode;
    }

    public void setRegCode(Integer regCode) {
        this.regCode = regCode;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public CityId() {
    }

    public CityId(Integer regCode, Integer areaCode, Integer cityCode) {
        this.regCode = regCode;
        this.areaCode = areaCode;
        this.cityCode = cityCode;
    }
}
