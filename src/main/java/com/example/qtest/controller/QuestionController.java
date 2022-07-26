package com.example.qtest.controller;

import com.example.qtest.model.Answer;
import com.example.qtest.model.Question;
import com.example.qtest.repository.QuestionRepository;
import com.example.qtest.service.QuestionService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("testId", id);
        List<Question> questionList = questionRepository.findAllByTestIdOrderById(id);
        model.addAttribute("questionList", questionList);
        return "qtest/administration/questionList";
    }

    @PostMapping("/questions/edit")
    @ResponseBody
    public HttpStatus editQuestion(@RequestParam Integer oldQuestionId, @ModelAttribute Question newQuestion){
        Question oldQuestion = questionRepository.findById(oldQuestionId).orElse(null);
        assert oldQuestion != null;
        oldQuestion.setDeleted(true);

        List<Answer> newAnswerList = oldQuestion.getAnswers();
        for (Answer answer: newAnswerList){
            answer.setId(null);
            answer.setQuestion(newQuestion);
        }
        newQuestion.setAnswers(newAnswerList);
        newQuestion.setTestId(oldQuestion.getTestId());
        newQuestion.setDeleted(false);

        questionRepository.save(newQuestion);
        questionRepository.save(oldQuestion);

        questionService.deleteUnusageQuestion(oldQuestion);
        return HttpStatus.OK;
    }

    @PostMapping("/questions/delete")
    public String deleteQuestion(@RequestParam Integer testId,
                                 @RequestParam Integer[] check){
       questionRepository.makeQuestionDeletedTrue(check);
       questionService.deleteUnusageQuestion(check);
       return "redirect:/tests/" + testId + "/questions";
    }
}
