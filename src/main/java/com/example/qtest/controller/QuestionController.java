package com.example.qtest.controller;

import com.example.qtest.model.Question;
import com.example.qtest.repository.QuestionRepository;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        List<Question> questionList = questionRepository.findAllByTestTestId(id);
        model.addAttribute("questionList", questionList);
        return "/qtest/questionList";
    }
}
