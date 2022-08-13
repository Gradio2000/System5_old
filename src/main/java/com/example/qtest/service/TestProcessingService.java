package com.example.qtest.service;

import com.example.qtest.model.ResultTest;
import com.example.qtest.repository.ResultTestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestProcessingService {
    private final ResultTestRepository resultTestRepository;

    public TestProcessingService(ResultTestRepository resultTestRepository) {
        this.resultTestRepository = resultTestRepository;
    }

    public void saveUserAnswer(Integer attemptId, Integer questionId, String[] answersIds){
        List<ResultTest> resultTestList = new ArrayList<>();
        assert answersIds != null : "saveUserAnswer() Пустой массив ответов на вопрос";
        for (String quesId : answersIds) {
            ResultTest resulttest = new ResultTest();
            resulttest.setAttemptId(attemptId);
            resulttest.setQuestionId(questionId);
            resulttest.setAnswerId(Integer.valueOf(quesId));
            resultTestList.add(resulttest);
        }
        resultTestRepository.saveAll(resultTestList);
    }
}
