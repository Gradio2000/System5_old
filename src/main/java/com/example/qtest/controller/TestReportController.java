package com.example.qtest.controller;

import com.example.qtest.model.Attempttest;
import com.example.qtest.model.FalseUsersAnswer;
import com.example.qtest.model.Question;
import com.example.qtest.model.ResultTest;
import com.example.qtest.repository.AttemptestReporitory;
import com.example.qtest.repository.FalseUsersAnswerRepository;
import com.example.qtest.repository.QuestionRepository;
import com.example.qtest.repository.ResultTestRepository;
import com.example.qtest.service.ResultTestService;
import com.example.system5.dto.UserDto;
import com.example.system5.dto.UserDtoNameOnlyWithPositionDto;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("qtest/report")
@Slf4j
public class TestReportController {
    private final AttemptestReporitory attemptestReporitory;
    private final ResultTestRepository resultTestRepository;
    private final ResultTestService resultTestService;
    private final QuestionRepository questionRepository;
    private final FalseUsersAnswerRepository falseUsersAnswerRepository;

    public TestReportController(AttemptestReporitory attemptestReporitory, ResultTestRepository resultTestRepository, ResultTestService resultTestService,
                                QuestionRepository questionRepository,
                                FalseUsersAnswerRepository falseUsersAnswerRepository) {
        this.attemptestReporitory = attemptestReporitory;
        this.resultTestRepository = resultTestRepository;
        this.resultTestService = resultTestService;
        this.questionRepository = questionRepository;
        this.falseUsersAnswerRepository = falseUsersAnswerRepository;
    }

    @GetMapping("/{attemptId}")
    public String getReport(@PathVariable Integer attemptId, @AuthenticationPrincipal AuthUser authUser,
                            Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        List<ResultTest> resultTestList = resultTestRepository.findAllByAttemptIdOrderById(attemptId);
        Map<Integer, List<Integer>> mapOfUserAnswers = resultTestService.getMapOfAnswers(resultTestList);
        List<Integer> listOfUsersAnswers = resultTestService.getListOfUsersAnswers(mapOfUserAnswers);
        List<Question> quesList = questionRepository.findQuestionsByAttemptId(attemptId);

        List<Integer> falseUserAnswers = falseUsersAnswerRepository.findAllByAttemptId(attemptId).stream()
                .map(FalseUsersAnswer::getQuestionId)
                .collect(Collectors.toList());

        Attempttest attempt = attemptestReporitory.findById(attemptId).orElse(null);
        UserDtoNameOnlyWithPositionDto userDtoNameOnlyWithPositionDto = UserDtoNameOnlyWithPositionDto.getInstance(attempt.getUser());
        model.addAttribute("falseUserAnswers", falseUserAnswers);
        model.addAttribute("quesList", quesList);
        model.addAttribute("listOfUsersAnswers", listOfUsersAnswers);
        model.addAttribute("userDtoNameOnlyWithPositionDto", userDtoNameOnlyWithPositionDto);
        model.addAttribute("attempt", attempt);

        return "qtest/report";
    }
}
