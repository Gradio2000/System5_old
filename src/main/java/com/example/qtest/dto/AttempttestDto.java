package com.example.qtest.dto;


import com.example.qtest.model.Attempttest;
import lombok.Getter;

import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AttempttestDto implements Serializable {
    private Date dateTime;
    private Integer amountQues;
    private Integer amountFalseAnswers;
    private Integer amountTrueAnswers;
    private Double result;
    private String testResult;
    private String consolidTestName;
    private Boolean consolidTest;

    public AttempttestDto(Date dateTime, Integer amountQues, Integer amountFalseAnswers,
                          Integer amountTrueAnswers, Double result, String testResult,
                          String consolidTestName, Boolean consolidTest) {
        this.dateTime = dateTime;
        this.amountQues = amountQues;
        this.amountFalseAnswers = amountFalseAnswers;
        this.amountTrueAnswers = amountTrueAnswers;
        this.result = result;
        this.testResult = testResult;
        this.consolidTestName = consolidTestName;
        this.consolidTest = consolidTest;
    }

    public static AttempttestDto getInstance(Attempttest attempttest){
        return new AttempttestDto(attempttest.getDateTime(), attempttest.getAmountQues(),
                attempttest.getAmountFalseAnswers(), attempttest.getAmountTrueAnswers(), attempttest.getResult(),
                attempttest.getTestResult(), attempttest.getConsolidTestName(), attempttest.getConsolidTest());
    }
}
