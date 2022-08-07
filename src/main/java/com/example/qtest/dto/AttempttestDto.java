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
    private Integer timeAttempt;
    private Integer amountQues;
    private Integer amountFalseAnswers;
    private Integer amountTrueAnswers;
    private Double result;
    private String testResult;

    public AttempttestDto(Date dateTime, Integer timeAttempt, Integer amountQues,
                          Integer amountFalseAnswers, Integer amountTrueAnswers, Double result,
                          String testResult) {
        this.dateTime = dateTime;
        this.timeAttempt = timeAttempt;
        this.amountQues = amountQues;
        this.amountFalseAnswers = amountFalseAnswers;
        this.amountTrueAnswers = amountTrueAnswers;
        this.result = result;
        this.testResult = testResult;
    }

    public static AttempttestDto getInstance(Attempttest attempttest){
        return new AttempttestDto(attempttest.getDateTime(), attempttest.getTimeAttempt(), attempttest.getAmountQues(),
                attempttest.getAmountFalseAnswers(), attempttest.getAmountTrueAnswers(), attempttest.getResult(),
                attempttest.getTestResult());
    }
}
