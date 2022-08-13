package com.example.qtest.controller;

import com.example.qtest.model.Question;
import com.example.qtest.repository.QuestionRepository;
import com.example.qtest.service.QuestionService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class QuestionController {
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public QuestionController(QuestionRepository questionRepository, QuestionService questionService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @GetMapping("/tests/{id}/questions")
    public String getQuestionsList(@AuthenticationPrincipal AuthUser authUser,
                                   Model model, @PathVariable Integer id){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("testId", id);
        List<Question> questionList = questionRepository.findAllByTestIdOrderById(id);
        model.addAttribute("questionList", questionList);
        return "qtest/administration/questionList";
    }

    @PostMapping("/questions/edit")
    @ResponseBody
    public HttpStatus editQuestion(@RequestParam Integer oldQuestionId, @ModelAttribute Question newQuestion,
                                   @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        questionService.editQuestion(oldQuestionId, newQuestion);
        return HttpStatus.OK;
    }

    @PostMapping("/questions/delete")
    public String deleteQuestion(@RequestParam Integer testId,
                                 @RequestParam Integer[] check, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

       questionRepository.makeQuestionDeletedTrue(check);
       questionService.deleteUnusageQuestion(check);
       return "redirect:/tests/" + testId + "/questions";
    }
}
