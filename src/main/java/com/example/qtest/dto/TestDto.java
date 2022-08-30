package com.example.qtest.dto;

import com.example.qtest.model.Test;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestDto implements Serializable {
    private  Integer testId;
    private  String testName;
    private  Double criteria;
    private  Boolean deleted;
    private  Integer quesAmount;


    public TestDto(Integer testId, String testName, Double criteria, Boolean deleted, Integer quesAmount) {
        this.testId = testId;
        this.testName = testName;
        this.criteria = criteria;
        this.deleted = deleted;
        this.quesAmount = quesAmount;
    }

    public static TestDto getInstance(Test test){
        return new TestDto(test.getTestId(), test.getTestName(), test.getCriteria(),
                test.getDeleted(), test.getQuestions().size());
    }
}
