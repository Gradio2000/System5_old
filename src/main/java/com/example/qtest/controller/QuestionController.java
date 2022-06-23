package com.example.qtest.controller;

import com.example.qtest.model.Question;
import com.example.qtest.repository.QuestionRepository;
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

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
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

    @PostMapping("//questions/edit/{id}")
    @ResponseBody
    public HttpStatus editQuestion(@PathVariable Integer id, @ModelAttribute Question question){
        Question questionForEdit = questionRepository.findById(id).orElse(null);
        assert questionForEdit != null;
        questionForEdit.setQuestionName(question.getQuestionName());
        questionRepository.save(questionForEdit);
        return HttpStatus.OK;
    }
}
