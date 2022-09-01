package com.example.qtest.service;

import com.example.qtest.model.Question;
import com.example.qtest.model.QuestionsForAttempt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestService {

    public Set<Question> getShuffleTest(Set<Question> questionList, Integer quesAmount) {
        Collections.shuffle(Arrays.asList(questionList.toArray()));
        return questionList.stream()
                .limit(quesAmount)
                .collect(Collectors.toSet());
    }

    public List<QuestionsForAttempt> convertTestForSaveBeforeTesting(Set<Question> questions, Integer attemptId){
        List<QuestionsForAttempt> questionsForAttemptList = new ArrayList<>();
        questions.forEach(q -> {
            questionsForAttemptList.add(new QuestionsForAttempt(attemptId, q));
        });
        return questionsForAttemptList;
    }
}
