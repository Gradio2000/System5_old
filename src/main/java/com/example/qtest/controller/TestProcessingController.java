package com.example.qtest.controller;

import com.example.qtest.model.Attempttest;
import com.example.qtest.model.QuestionsForAttempt;
import com.example.qtest.model.ResultTest;
import com.example.qtest.model.Test;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.QuestionForAttemptRepository;
import com.example.qtest.repository.ResultTestRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.ResultTestService;
import com.example.qtest.service.TestService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/processing")
public class TestProcessingController {
    private final AttemptestReporitory attemptestReporitory;
    private final TestReposytory testReposytory;
    private final TestService testService;
    private final QuestionForAttemptRepository questionForAttemptRepository;
    private final ResultTestRepository resultTestRepository;
    private final ResultTestService resultTestService;


    public TestProcessingController(AttemptestReporitory attemptestReporitory,
                                    TestReposytory testReposytory, TestService testService,
                                    QuestionForAttemptRepository questionForAttemptRepository,
                                    ResultTestRepository resultTestRepository, ResultTestService resultTestService) {
        this.attemptestReporitory = attemptestReporitory;
        this.testReposytory = testReposytory;
        this.testService = testService;
        this.questionForAttemptRepository = questionForAttemptRepository;
        this.resultTestRepository = resultTestRepository;
        this.resultTestService = resultTestService;
    }

    @PostMapping("/start")
    public String startTestProcessing(@AuthenticationPrincipal AuthUser authUser,
                                      Model model, @RequestParam Integer testId){

            Attempttest attempttest = new Attempttest();
            attempttest.setDateTime(new Date());
            attempttest.setUserId(authUser.getUser().getUserId());
            attempttest.setTestId(testId);
            attemptestReporitory.save(attempttest);

            Test test = testReposytory.findById(testId).orElse(null);
            assert test != null;
            testService.getShuffleTest(test);

            List<QuestionsForAttempt> questionsForAttemptList = testService.convertTestForSaveBeforeTesting(test, attempttest.getId());
            questionForAttemptRepository.saveAll(questionsForAttemptList);

            model.addAttribute("attemptId", attempttest.getId());
            model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
            model.addAttribute("testName", test.getTestName());
            model.addAttribute("questionList", questionsForAttemptList);
            return "qtest/process";
    }

    @PostMapping("/saveUserAnswer")
    @ResponseBody
    public HttpStatus saveUserAnswer(@RequestParam Integer attemptId, @RequestParam Integer questionId,
                                     HttpServletRequest request){
        String[] answersIds = request.getParameterMap().get("check");
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
        return HttpStatus.OK;
    }

    @GetMapping("/finishTest/{attemptId}")
    public String finishTest(@PathVariable Integer attemptId){
        resultTestService.mainCheck(attemptId);
        return "redirect:/tests/mytests/0";
    }
}
