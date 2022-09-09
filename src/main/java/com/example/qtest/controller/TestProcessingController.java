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
import java.util.*;
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
    private final AppointTestAmountRepository appointTestAmountRepository;

    public TestProcessingController(AttemptestReporitory attemptestReporitory, TestReposytory testReposytory,
                                    TestService testService, QuestionForAttemptRepository questionForAttemptRepository,
                                    ResultTestRepository resultTestRepository, ResultTestService resultTestService,
                                    AppointTestRepository appointTestRepository, TestProcessingService testProcessingService,
                                    AppointTestAmountRepository appointTestAmountRepository) {
        this.attemptestReporitory = attemptestReporitory;
        this.testReposytory = testReposytory;
        this.testService = testService;
        this.questionForAttemptRepository = questionForAttemptRepository;
        this.resultTestRepository = resultTestRepository;
        this.resultTestService = resultTestService;
        this.appointTestRepository = appointTestRepository;
        this.testProcessingService = testProcessingService;
        this.appointTestAmountRepository = appointTestAmountRepository;
    }

    @PostMapping("/start")
    public String startTestProcessing(@AuthenticationPrincipal AuthUser authUser, Model model,
                                      @RequestParam Integer[] testIds,
                                      @RequestParam Integer[] quesAmounts,
                                      @RequestParam (required = false) Integer appointTestId,
                                      @RequestParam (required = false) Integer criteria,
                                      @RequestParam (required = false) String testName){

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        Map<Integer, Integer> idsQuesAmountMap = new HashMap<>();
        for (int i = 0; i < testIds.length; i++) {
            idsQuesAmountMap.put(testIds[i], quesAmounts[i]);
        }

            Attempttest attempttest = new Attempttest();
            attempttest.setDateTime(new Date());
            attempttest.setUser(authUser.getUser());
            if (criteria != null){
                attempttest.setCriteria(criteria);
            }
            else {
                attempttest.setCriteria(0);
            }

            attempttest.setTestResult("Не завершен");

            Set<Question> allQuestionsForTesting = new HashSet<>();

            if (idsQuesAmountMap.size() > 1){
                attempttest.setConsolidTest(true);
                attempttest.setTestName(testName);
                List<Test> testList = testReposytory.findByAllByIds(Arrays.asList(testIds));
                for (Test test: testList){
                    int quesAmount = idsQuesAmountMap.get(test.getTestId());
                    Set<Question> questionSet = new HashSet<>(test.getQuestions());
                    questionSet = testService.getShuffleTest(questionSet, quesAmount);
                    allQuestionsForTesting.addAll(questionSet);
                    test.setUsed(true);
                }
                allQuestionsForTesting = testService.getShuffleTest(allQuestionsForTesting, allQuestionsForTesting.size());
                testReposytory.saveAll(testList);
            }
            else {
                attempttest.setConsolidTest(false);
                Test test = testReposytory.findById(testIds[0]).orElse(null);
                assert test != null;
                attempttest.setTestName(test.getTestName());
                allQuestionsForTesting  = testService.getShuffleTest(test.getQuestions(), quesAmounts[0]);
                test.setUsed(true);
                testReposytory.save(test);
            }

            attemptestReporitory.save(attempttest);

            List<QuestionsForAttempt> questionsForAttemptList =
                    testService.convertTestForSaveBeforeTesting(allQuestionsForTesting, attempttest.getId());

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

        List<QuestionsForAttempt> questionsForAttemptList = questionForAttemptRepository.findAllByAttemptIdOrderById(attemptId);
        Set<Integer> resultTestQuestionsIdsList = resultTestRepository.findAllByAttemptIdOrderById(attemptId).stream()
                .map(ResultTest::getQuestionId)
                .collect(Collectors.toSet());
        Set<Integer> resultTestAnswerIdsList = resultTestRepository.findAllByAttemptIdOrderById(attemptId).stream()
                .map(ResultTest::getAnswerId)
                .collect(Collectors.toSet());

        int criteria = Objects.requireNonNull(attemptestReporitory.findById(attemptId).orElse(null)).getCriteria();

        model.addAttribute("attemptId", attemptId);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("questionList", questionsForAttemptList);
        model.addAttribute("resultTestQuestionsIdsList", resultTestQuestionsIdsList);
        model.addAttribute("resultTestAnswerIdsList", resultTestAnswerIdsList);
        model.addAttribute("criteria", criteria);
        return "qtest/process";
    }

    @GetMapping("/startExam")
    public String startExam(@AuthenticationPrincipal AuthUser authUser,
                            @RequestParam Integer appointTestId,
                            Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        AppointTest appointTest = appointTestRepository.findById(appointTestId).orElse(null);
        List<AppointTestAmount> appointTestAmountList = appointTestAmountRepository.findAllByAppointId(appointTestId);

        Attempttest attempttest = new Attempttest();
        attempttest.setDateTime(new Date());
        assert appointTest != null;
        attempttest.setUser(appointTest.getUser());
        attempttest.setCriteria(appointTest.getCriteria());
        attempttest.setTestResult("Не завершен");

        Set<Question> allQuestionsForTesting = new HashSet<>();
        if (appointTestAmountList.size() > 1){
            attempttest.setConsolidTest(true);
            attempttest.setTestName(appointTest.getTestName());
            List<Test> testList = testReposytory.findByAllByIds(appointTestAmountList.stream()
                    .map(AppointTestAmount::getTestId)
                    .collect(Collectors.toList()));

            for (Test test: testList){
                int quesAmount = Objects.requireNonNull(appointTestAmountList.stream()
                                .filter(appointTestAmount -> Objects.equals(appointTestAmount.getTestId(), test.getTestId()))
                                .findFirst().orElse(null))
                        .getQuesAmount();
                Set<Question> questionSet = new HashSet<>(test.getQuestions());
                questionSet = testService.getShuffleTest(questionSet, quesAmount);
                allQuestionsForTesting.addAll(questionSet);
                test.setUsed(true);
            }
            allQuestionsForTesting = testService.getShuffleTest(allQuestionsForTesting, allQuestionsForTesting.size());
            testReposytory.saveAll(testList);
        }
        else {
            attempttest.setConsolidTest(false);
            Test test = testReposytory.findById(appointTestAmountList.get(0).getTestId()).orElse(null);
            assert test != null;
            attempttest.setTestName(test.getTestName());
            allQuestionsForTesting  = testService.getShuffleTest(test.getQuestions(), appointTestAmountList.get(0).getQuesAmount());
            test.setUsed(true);
            testReposytory.save(test);
        }

        attemptestReporitory.save(attempttest);

        List<QuestionsForAttempt> questionsForAttemptList =
                testService.convertTestForSaveBeforeTesting(allQuestionsForTesting, attempttest.getId());

        questionForAttemptRepository.saveAll(questionsForAttemptList);

        appointTest.setAttempttest(attempttest);
        appointTestRepository.save(appointTest);

        model.addAttribute("questionList", questionsForAttemptList);
        model.addAttribute("attemptId", attempttest.getId());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("appointTestId", appointTestId);
        model.addAttribute("criteria", appointTest.getCriteria());

        return "qtest/process";
    }
}
