package com.example.qtest.service;

import com.example.qtest.model.*;
import com.example.qtest.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ResultTestService {

    private final AttemptestReporitory attemptestReporitory;
    private final ResultTestRepository resultTestRepository;
    private final QuestionForAttemptRepository questionForAttemptRepository;
    private final TestReposytory testReposytory;
    private final QuestionRepository questionRepository;

    public ResultTestService(AttemptestReporitory attemptestReporitory, ResultTestRepository resultTestRepository, QuestionForAttemptRepository questionForAttemptRepository, TestReposytory testReposytory, QuestionRepository questionRepository) {
        this.attemptestReporitory = attemptestReporitory;
        this.resultTestRepository = resultTestRepository;
        this.questionForAttemptRepository = questionForAttemptRepository;
        this.testReposytory = testReposytory;
        this.questionRepository = questionRepository;
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
    public void mainCheck(Integer attemptId) {

        String date = null;
        Set<Integer> falseAnswerSet;
        int trueAnswers;
        double result;
        String testResult;
        List<Integer> listOfUsersAnswers = null;
        String time = null;
        List<Question> quesList;


        Attempttest attemptTest = attemptestReporitory.findById(attemptId).orElse(null);
        assert attemptTest != null;

        Test test = testReposytory.findById(attemptTest.getTestId()).orElse(null);
        assert test != null;

        List<ResultTest> resultTestList = getResultTest(attemptId);
        Map<Integer, List<Integer>> mapOfUserAnswers = getMapOfAnswers(resultTestList);
        quesList = questionRepository.findQuestionsByAttemptId(attemptId);
        falseAnswerSet = getFalseAnswerSet(mapOfUserAnswers, quesList);
        trueAnswers = quesList.size() - falseAnswerSet.size();
        result = getResult(trueAnswers, quesList.size());
        testResult = getTestResult(result, test.getCriteria()) ? "Тест пройден" : "Тест не пройден";
        listOfUsersAnswers = getListOfUsersAnswers(mapOfUserAnswers);

        //Ок. А теперь кое-что запишем в бд, чтоб админ мог использовать
        attemptTest.setAmountQues(quesList.size());
        attemptTest.setAmountFalseAnswers(falseAnswerSet.size());
        attemptTest.setAmountTrueAnswers(trueAnswers);
        attemptTest.setResult(result);
        attemptTest.setTestResult(testResult);
        attemptestReporitory.save(attemptTest);


//        } catch (Exception e) {
//            FileHandler fh = new FileHandler("your_log.txt", false);   // true forces append mode
//            SimpleFormatter sf = new SimpleFormatter();
//            fh.setFormatter(sf);
//            log.addHandler(fh);
//            log.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
//        }

//        return new Statistic(date, test, falseAnswerSet, trueAnswers, testResult, listOfUsersAnswers, result, time, quesList);
    }

    public List<ResultTest> getResultTest(Integer attemptId) {
        return resultTestRepository.findAllByAttemptId(attemptId);
    }

    public Double getResult(int trueCountAnswers, int totalCountAnswers) {
        BigDecimal bigDecimal1 = new BigDecimal(trueCountAnswers);
        BigDecimal bigDecimal2 = new BigDecimal(totalCountAnswers);
        return bigDecimal2.compareTo(BigDecimal.ZERO) == 0 ? 0 : bigDecimal1.divide(bigDecimal2, 2, RoundingMode.DOWN).multiply(new BigDecimal("100")).doubleValue();
    }

    private boolean getTestResult(Double result, Double criteria) {
        return result >= criteria;
    }

}
