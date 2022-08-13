package com.example.qtest.controller;

import com.example.qtest.model.Question;
import com.example.qtest.service.QuestionService;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AnswerController {
    private final QuestionService questionService;

    public AnswerController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/answer/edit")
    @ResponseBody
    public HttpStatus editAnswer(@RequestParam Integer id, @RequestParam String answerName,
                                 @RequestParam String isRight, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        Question oldQuestion = questionService.makeQuestionDeletedTrue(id);
        questionService.editAnswer(oldQuestion, id, isRight, answerName);
        questionService.deleteUnusageQuestion(oldQuestion);
        return HttpStatus.OK;
    }
}
