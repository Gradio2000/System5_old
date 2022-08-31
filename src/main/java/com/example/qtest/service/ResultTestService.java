package com.example.qtest.service;

import com.example.qtest.model.*;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.FalseUsersAnswerRepository;
import com.example.qtest.repository.QuestionRepository;
import com.example.qtest.repository.ResultTestRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ResultTestService {

    private final AttemptestReporitory attemptestReporitory;
    private final ResultTestRepository resultTestRepository;
    private final QuestionRepository questionRepository;
    private final FalseUsersAnswerRepository falseUsersAnswerRepository;

    public ResultTestService(AttemptestReporitory attemptestReporitory,
                             ResultTestRepository resultTestRepository,
                             QuestionRepository questionRepository,
                             FalseUsersAnswerRepository falseUsersAnswerRepository) {
        this.attemptestReporitory = attemptestReporitory;
        this.resultTestRepository = resultTestRepository;
        this.questionRepository = questionRepository;
        this.falseUsersAnswerRepository = falseUsersAnswerRepository;
    }

    public Map<Integer, List<Integer>> getMapOfAnswers(List<ResultTest> resultTestList) {
        Map<Integer, List<Integer>> mapOfUserAnswers = new HashMap<>();
        for (ResultTest resultTest : resultTestList) {
            List<Integer> answersIdList = mapOfUserAnswers.get(resultTest.getQuestionId());
            if (answersIdList == null) answersIdList = new ArrayList<>();
            answersIdList.add(resultTest.getAnswerId());
            mapOfUserAnswers.put(resultTest.getQuestionId(), answersIdList);
        }
        return mapOfUserAnswers;
    }

    public Set<Integer> getFalseAnswerSet(Map<Integer, List<Integer>> mapOfUserAnswers, List<Question> questionList) {

        Set<Integer> falseAnswerSet = new HashSet<>();
        if (mapOfUserAnswers.size() != 0) {
            for (Question question : questionList) {
                List<Answer> answerList = question.getAnswers();
                for (Answer answer : answerList) {
                    List<Integer> questionsIdList = mapOfUserAnswers.get(question.getId());
                    if (questionsIdList == null) {
                        falseAnswerSet.add(question.getId());
                    } else {
                        if (answer.getIsRight()) {
                            if (!questionsIdList.contains(answer.getId())) {
                                falseAnswerSet.add(question.getId());
                                break;
                            }
                        } else {
                            if (questionsIdList.contains(answer.getId())) {
                                falseAnswerSet.add(question.getId());
                            }
                        }
                    }
                }
            }
        } else {
            for (Question question : questionList) {
                falseAnswerSet.add(question.getId());
            }
        }

        return falseAnswerSet;
    }

    public List<Integer> getListOfUsersAnswers(Map<Integer, List<Integer>> mapOfUserAnswers) {
        List<Integer> listOfUsersAnswers = new ArrayList<>();
        for (Integer key : mapOfUserAnswers.keySet()) {
            listOfUsersAnswers.addAll(mapOfUserAnswers.get(key));
        }
        return listOfUsersAnswers;
    }

    //основной метод проверки ответов пользователя и вывода результата теста после его прохождения
    public void mainCheck(Integer attemptId, Integer criteria) {

        List<ResultTest> resultTestList = getResultTest(attemptId);
        Map<Integer, List<Integer>> mapOfUserAnswers = getMapOfAnswers(resultTestList);
        List<Question> quesList = questionRepository.findQuestionsByAttemptId(attemptId);
        Set<Integer> falseAnswerSet = getFalseAnswerSet(mapOfUserAnswers, quesList);
        int trueAnswers = quesList.size() - falseAnswerSet.size();
        double result = getResult(trueAnswers, quesList.size());

        Attempttest attemptTest = attemptestReporitory.findById(attemptId).orElse(null);
        assert attemptTest != null;
        String testResult = getTestResult(result, criteria) ? "Удовлетворительно" : "Неудовлетворительно";

        //Ок. А теперь кое-что запишем в бд, чтоб админ мог использовать
        attemptTest.setAmountQues(quesList.size());
        attemptTest.setAmountFalseAnswers(falseAnswerSet.size());
        attemptTest.setAmountTrueAnswers(trueAnswers);
        attemptTest.setResult(result);
        attemptTest.setTestResult(testResult);
        attemptestReporitory.save(attemptTest);

        saveUserFalseAnswerToDB(attemptId, falseAnswerSet);
    }

    private void saveUserFalseAnswerToDB(Integer attemptId, Set<Integer> falseAnswerSet) {
        List<FalseUsersAnswer> falseUsersAnswerList = new ArrayList<>();
        for (Integer id : falseAnswerSet){
            FalseUsersAnswer falseUsersAnswer = new FalseUsersAnswer();
            falseUsersAnswer.setAttemptId(attemptId);
            falseUsersAnswer.setQuestionId(id);
            falseUsersAnswerList.add(falseUsersAnswer);
        }
        falseUsersAnswerRepository.saveAll(falseUsersAnswerList);
    }

    public List<ResultTest> getResultTest(Integer attemptId) {
        return resultTestRepository.findAllByAttemptId(attemptId);
    }

    public Double getResult(int trueCountAnswers, int totalCountAnswers) {
        BigDecimal bigDecimal1 = new BigDecimal(trueCountAnswers);
        BigDecimal bigDecimal2 = new BigDecimal(totalCountAnswers);
        return bigDecimal2.compareTo(BigDecimal.ZERO) == 0 ? 0 : bigDecimal1.divide(bigDecimal2, 2, RoundingMode.DOWN).multiply(new BigDecimal("100")).doubleValue();
    }

    private boolean getTestResult(Double result, Integer criteria) {
        return result >= criteria;
    }

}
