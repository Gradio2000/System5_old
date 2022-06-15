package com.example.qtest.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestDto implements Serializable {
    private final Integer testId;
    private final String testName;
    private final Double criteria;
    private final Double time;
    private final Integer quesAmount;
    private final boolean quesMix;
}
