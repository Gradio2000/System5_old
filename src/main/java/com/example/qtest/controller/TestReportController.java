package com.example.qtest.controller;

import com.example.qtest.model.Question;
import com.example.qtest.model.ResultTest;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.QuestionRepository;
import com.example.qtest.repository.ResultTestRepository;
import com.example.qtest.service.ResultTestService;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("qtest/report")
@Slf4j
public class TestReportController {
    private final AttemptestReporitory attemptestReporitory;
    private final ResultTestRepository resultTestRepository;
    private final ResultTestService resultTestService;
    private final QuestionRepository questionRepository;

    public TestReportController(AttemptestReporitory attemptestReporitory, ResultTestRepository resultTestRepository, ResultTestService resultTestService, QuestionRepository questionRepository) {
        this.attemptestReporitory = attemptestReporitory;
        this.resultTestRepository = resultTestRepository;
        this.resultTestService = resultTestService;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/{attemptId}")
    public String getReport(@PathVariable Integer attemptId, @AuthenticationPrincipal AuthUser authUser,
                            Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", authUser.getUser());
        model.addAttribute("attempt", attemptestReporitory.findById(attemptId).orElse(null));

        List<ResultTest> resultTestList = resultTestRepository.findAllByAttemptId(attemptId);
        Map<Integer, List<Integer>> mapOfUserAnswers = resultTestService.getMapOfAnswers(resultTestList);
        List<Integer> listOfUsersAnswers = resultTestService.getListOfUsersAnswers(mapOfUserAnswers);
        List<Question> quesList = questionRepository.findQuestionsByAttemptId(attemptId);


        model.addAttribute("quesList", quesList);
        model.addAttribute("listOfUsersAnswers", listOfUsersAnswers);
        return "qtest/report";
    }
}
