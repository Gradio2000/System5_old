package com.example.qtest.controller;

import com.example.qtest.model.Answer;
import com.example.qtest.repository.AnswerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnswerController {
    private final AnswerRepository answerRepository;

    public AnswerController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @PostMapping("/answer/edit/{id}")
    @ResponseBody
    public HttpStatus editAnswer(@PathVariable Integer id, @RequestParam String answerName,
                                 @RequestParam String isRight){
        Answer answerForEdit = answerRepository.findById(id).orElse(null);
        boolean isRightAnswer = !isRight.isEmpty();
        assert answerForEdit != null;
        answerForEdit.setIsRight(isRightAnswer);
        answerForEdit.setAnswerName(answerName);
        answerRepository.save(answerForEdit);
        return HttpStatus.OK;
    }
}
