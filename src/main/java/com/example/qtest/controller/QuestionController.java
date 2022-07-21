package com.example.qtest.controller;

import com.example.qtest.model.Answer;
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

    @PostMapping("/questions/edit/{oldQuestionId}")
    @ResponseBody
    public HttpStatus editQuestion(@PathVariable Integer oldQuestionId, @ModelAttribute Question newQuestion){
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

        return HttpStatus.OK;
    }
}
