package com.example.kladr.config;

import java.io.Serializable;
import java.util.Objects;

public class AreaId implements Serializable {
    private Integer regCode;
    private Integer areaCode;

    public AreaId() {
    }

    public AreaId(Integer regCode, Integer areaCode) {
        this.regCode = regCode;
        this.areaCode = areaCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaId that = (AreaId) o;
        return Objects.equals(regCode, that.regCode) && Objects.equals(areaCode, that.areaCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regCode, areaCode);
    }
}
