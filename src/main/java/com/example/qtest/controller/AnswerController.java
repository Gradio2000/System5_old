package com.example.qtest.controller;

import com.example.qtest.model.Answer;
import com.example.qtest.model.Question;
import com.example.qtest.repository.AnswerRepository;
import com.example.qtest.repository.QuestionRepository;
import com.example.qtest.service.QuestionService;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
public class AnswerController {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public AnswerController(AnswerRepository answerRepository,
                            QuestionRepository questionRepository,
                            QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @PostMapping("/answer/edit")
    @ResponseBody
    public HttpStatus editAnswer(@RequestParam Integer id, @RequestParam String answerName,
                                 @RequestParam String isRight, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        Answer oldAnswer = answerRepository.findById(id).orElse(null);
        assert oldAnswer != null;
        Question oldQuestion = oldAnswer.getQuestion();
        oldQuestion.setDeleted(true);
        questionRepository.save(oldQuestion);

        Question newQuestion = questionService.makeQuestionCopy(oldQuestion);
        newQuestion.setDeleted(false);
        newQuestion.setId(null);
        List<Answer> answerList = newQuestion.getAnswers();
        for (Answer answer: answerList){
            if (Objects.equals(answer.getId(), id)){
                boolean isRightAnswer = !isRight.isEmpty();
                answer.setIsRight(isRightAnswer);
                answer.setAnswerName(answerName);
            }
            answer.setId(null);
        }

        questionRepository.save(newQuestion);
        questionService.deleteUnusageQuestion(oldQuestion);
        return HttpStatus.OK;
    }
}
