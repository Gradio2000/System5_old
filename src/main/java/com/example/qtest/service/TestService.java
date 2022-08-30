package com.example.qtest.service;

import com.example.qtest.model.Question;
import com.example.qtest.model.QuestionsForAttempt;
import com.example.qtest.model.Test;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestService {

    public void getShuffleTest(Test test, Integer quesAmount) {
        List<Question> questionList = new ArrayList<>(test.getQuestions());
        Collections.shuffle(questionList);
        test.setQuestions(questionList.stream()
                .limit(quesAmount)
                .collect(Collectors.toSet()));
    }

    public List<QuestionsForAttempt> convertTestForSaveBeforeTesting(Test test, Integer attemptId){
        List<QuestionsForAttempt> questionsForAttemptList = new ArrayList<>();
        Set<Question> questions = test.getQuestions();
        questions.forEach(q -> {
            questionsForAttemptList.add(new QuestionsForAttempt(attemptId, q));
        });
        return questionsForAttemptList;
    }
}
