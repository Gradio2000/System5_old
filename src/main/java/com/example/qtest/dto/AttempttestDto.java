package com.example.qtest.dto;


import com.example.qtest.model.Attempttest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AttempttestDto implements Serializable {
    private Integer id;
    private Date dateTime;
    private Integer amountQues;
    private Integer amountFalseAnswers;
    private Integer amountTrueAnswers;
    private Double result;
    private String testResult;
    private String testName;
    private Boolean consolidTest;

    public AttempttestDto(Integer id, Date dateTime, Integer amountQues, Integer amountFalseAnswers,
                          Integer amountTrueAnswers, Double result, String testResult,
                          String testName, Boolean consolidTest) {
        this.id = id;
        this.dateTime = dateTime;
        this.amountQues = amountQues;
        this.amountFalseAnswers = amountFalseAnswers;
        this.amountTrueAnswers = amountTrueAnswers;
        this.result = result;
        this.testResult = testResult;
        this.testName = testName;
        this.consolidTest = consolidTest;
    }

    public static AttempttestDto getInstance(Attempttest attempttest){
        return new AttempttestDto(attempttest.getId(), attempttest.getDateTime(), attempttest.getAmountQues(),
                attempttest.getAmountFalseAnswers(), attempttest.getAmountTrueAnswers(), attempttest.getResult(),
                attempttest.getTestResult(), attempttest.getTestName(), attempttest.getConsolidTest());
    }
}
