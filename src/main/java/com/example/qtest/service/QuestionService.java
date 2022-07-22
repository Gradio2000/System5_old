package com.example.qtest.service;

import com.example.qtest.model.Question;
import com.example.qtest.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
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
}
