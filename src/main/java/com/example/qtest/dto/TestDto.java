package com.example.qtest.dto;

import com.example.qtest.model.Test;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestDto implements Serializable {
    private  Integer testId;
    private  String testName;
    private  Double criteria;
    private  Double time;
    private  Integer quesAmount;
    private  Boolean quesMix;
    private  Boolean deleted;


    public TestDto(Integer testId, String testName, Double criteria, Double time,
                   Integer quesAmount, Boolean quesMix, Boolean deleted) {
        this.testId = testId;
        this.testName = testName;
        this.criteria = criteria;
        this.time = time;
        this.quesAmount = quesAmount;
        this.quesMix = quesMix;
        this.deleted = deleted;
    }

    public static TestDto getInstance(Test test){
        return new TestDto(test.getTestId(), test.getTestName(), test.getCriteria(),
                test.getTime(), test.getQuesAmount(),test.getQuesMix(), test.getDeleted());
    }
}
