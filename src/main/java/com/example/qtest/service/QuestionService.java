package com.example.qtest.service;

import com.example.qtest.model.Answer;
import com.example.qtest.model.Question;
import com.example.qtest.repository.AnswerRepository;
import com.example.qtest.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Question makeQuestionCopy(Question question){
        Question copyQuestion;
        try {
            //сохраняем состояние объекта в поток и закрываем его(поток)
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(question);
            ous.close();
            //создаём копию объекта
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            copyQuestion = (Question) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return copyQuestion;
    }

    public void deleteUnusageQuestion(Question question){
        Set<Integer> usageTestIds = questionRepository.getIds();
        if (!usageTestIds.contains(question.getId())){
            questionRepository.delete(question);
        }
    }

    public void deleteUnusageQuestion(Integer[] ids){
        Set<Integer> usageTestIds = questionRepository.getIds();
        Set<Integer> idsSet =new HashSet<>(Arrays.asList(ids));
        idsSet.removeIf(usageTestIds::contains);
        questionRepository.deleteAllById(idsSet);
    }

    public Question makeQuestionDeletedTrue(Integer answerId){
        Answer oldAnswer = answerRepository.findById(answerId).orElse(null);
        assert oldAnswer != null;
        Question oldQuestion = oldAnswer.getQuestion();
        oldQuestion.setDeleted(true);
        return questionRepository.save(oldQuestion);
    }

    public void editAnswer(Question oldQuestion, Integer id, String isRight, String answerName){
        Question newQuestion = makeQuestionCopy(oldQuestion);
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
    }

    public void editQuestion(Integer oldQuestionId, Question newQuestion){
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

        deleteUnusageQuestion(oldQuestion);
    }
}
