package com.example.qtest.service;

import com.example.qtest.model.Answer;
import com.example.qtest.model.Question;
import com.example.qtest.model.Test;
import com.example.qtest.repository.TestReposytory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class FileUploadService {
    private final TestReposytory testReposytory;

    public FileUploadService(TestReposytory testReposytory) {
        this.testReposytory = testReposytory;
    }

    public void uploadQuestionFromFile(MultipartFile file, Integer testId) throws IOException, ArrayIndexOutOfBoundsException {
        File newFile = File.createTempFile("temp", null, null);
        file.transferTo(newFile);

        BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(newFile.toPath())));

        String line;
        Question question;
        Answer answer;
        List<Question> questionList = new ArrayList<>();
        List<Answer> answerList;

        int i = 0;
        while ((line = reader.readLine()) != null) {
            String[] lineMass = line.split(";");

            //проверяем первую ячейку
            if (!lineMass[0].equals("")){
                question = new Question();
                question.setQuestionName(lineMass[0]);
                answerList = new ArrayList<>();
                i++;
            }
            else {
                if (questionList.size() != 0){
                    question = questionList.remove(i - 1);
                    answerList = question.getAnswers();
                }
                else {
                    question = new Question();
                    answerList = new ArrayList<>();
                }
            }

            answer = new Answer();

            //проверяем вторую ячейку
            if (!lineMass[1].equals("")){
                answer.setAnswerName(lineMass[1]);
            }

            //проверяем третью ячейку
            if (lineMass.length == 3) {
                answer.setIsRight(true);
            }

            answer.setQuestion(question);
            answerList.add(answer);
            question.setAnswers(answerList);
            question.setTestId(testId);
            question.setDeleted(false);
            questionList.add(question);
        }
        reader.close();
        Test test = testReposytory.findById(testId).orElse(null);
        assert test != null;
        test.setQuestions(new HashSet<>(questionList));
        testReposytory.save(test);
    }
}
