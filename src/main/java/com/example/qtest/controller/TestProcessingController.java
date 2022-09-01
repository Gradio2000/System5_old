package com.example.qtest.controller;

import com.example.qtest.model.*;
import com.example.qtest.repository.*;
import com.example.qtest.service.ResultTestService;
import com.example.qtest.service.TestProcessingService;
import com.example.qtest.service.TestService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/processing")
@Slf4j
public class TestProcessingController {
    private final AttemptestReporitory attemptestReporitory;
    private final TestReposytory testReposytory;
    private final TestService testService;
    private final QuestionForAttemptRepository questionForAttemptRepository;
    private final ResultTestRepository resultTestRepository;
    private final ResultTestService resultTestService;
    private final AppointTestRepository appointTestRepository;
    private final TestProcessingService testProcessingService;

    public TestProcessingController(AttemptestReporitory attemptestReporitory, TestReposytory testReposytory,
                                    TestService testService, QuestionForAttemptRepository questionForAttemptRepository,
                                    ResultTestRepository resultTestRepository, ResultTestService resultTestService,
                                    AppointTestRepository appointTestRepository, TestProcessingService testProcessingService) {
        this.attemptestReporitory = attemptestReporitory;
        this.testReposytory = testReposytory;
        this.testService = testService;
        this.questionForAttemptRepository = questionForAttemptRepository;
        this.resultTestRepository = resultTestRepository;
        this.resultTestService = resultTestService;
        this.appointTestRepository = appointTestRepository;
        this.testProcessingService = testProcessingService;
    }

    @PostMapping("/start")
    public String startTestProcessing(@AuthenticationPrincipal AuthUser authUser, Model model,
                                      @RequestParam (required = false) Integer testId,
                                      @RequestParam (required = false) Integer appointTestId,
                                      @RequestParam Integer quesAmount,
                                      @RequestParam Integer criteria,
                                      @RequestParam (required = false) List<Integer> testIds,
                                      @RequestParam Boolean consolidTest,
                                      @RequestParam (required = false) String testName){

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

            Attempttest attempttest = new Attempttest();
            attempttest.setDateTime(new Date());
            attempttest.setUser(authUser.getUser());
            attempttest.setCriteria(criteria);
            attempttest.setTestResult("Не завершен");

            Set<Question> questionSet = new HashSet<>();

            if (consolidTest){
                attempttest.setConsolidTest(true);
                attempttest.setConsolidTestName(testName);
                //todo
                List<Test> testList = testReposytory.findByAllByIds(testIds);
                for (Test test: testList){
                    questionSet.addAll(test.getQuestions());
                }
                questionSet = testService.getShuffleTest(questionSet, quesAmount);
            }
            else {
                Test test = testReposytory.findById(testId).orElse(null);
                assert test != null;
                questionSet  = testService.getShuffleTest(test.getQuestions(), quesAmount);
            }

            attemptestReporitory.save(attempttest);

            List<QuestionsForAttempt> questionsForAttemptList =
                    testService.convertTestForSaveBeforeTesting(questionSet, attempttest.getId());
            questionForAttemptRepository.saveAll(questionsForAttemptList);

            //блок для зачета
            if (appointTestId != null){
                AppointTest appointTest = appointTestRepository.findById(appointTestId).orElse(null);
                assert appointTest != null;
                appointTest.setAttempttest(attempttest);
                appointTestRepository.save(appointTest);
            }

        model.addAttribute("questionList", questionsForAttemptList);
        model.addAttribute("attemptId", attempttest.getId());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("appointTestId", appointTestId);
        model.addAttribute("criteria", criteria);
        return "qtest/process";
    }

    @PostMapping("/saveUserAnswer")
    @ResponseBody
    public HttpStatus saveUserAnswer(@RequestParam Integer attemptId, @RequestParam Integer questionId,
                                     HttpServletRequest request, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        String[] answersIds = request.getParameterMap().get("check");
        testProcessingService.saveUserAnswer(attemptId, questionId, answersIds);
        return HttpStatus.OK;
    }

    @PostMapping("/finishTest")
    public String finishTest(@RequestParam Integer attemptId,
                             @RequestParam (required = false) Integer appointTestId,
                             @AuthenticationPrincipal AuthUser authUser,
                             @RequestParam Integer criteria){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        resultTestService.mainCheck(attemptId, criteria);
        if (appointTestId != null){
            appointTestRepository.setAppointTrue(appointTestId);
        }
        return "redirect:/tests/mytests";
    }

    @GetMapping("/continue/{attemptId}")
    public String continueTest(@AuthenticationPrincipal AuthUser authUser,
                               @PathVariable Integer attemptId, Model model){

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        List<QuestionsForAttempt> questionsForAttemptList = questionForAttemptRepository.findAllByAttemptId(attemptId);
        Set<Integer> resultTestQuestionsIdsList = resultTestRepository.findAllByAttemptId(attemptId).stream()
                .map(ResultTest::getQuestionId)
                .collect(Collectors.toSet());
        Set<Integer> resultTestAnswerIdsList = resultTestRepository.findAllByAttemptId(attemptId).stream()
                .map(ResultTest::getAnswerId)
                .collect(Collectors.toSet());

        model.addAttribute("attemptId", attemptId);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("questionList", questionsForAttemptList);
        model.addAttribute("resultTestQuestionsIdsList", resultTestQuestionsIdsList);
        model.addAttribute("resultTestAnswerIdsList", resultTestAnswerIdsList);
        return "qtest/process";
    }
}
