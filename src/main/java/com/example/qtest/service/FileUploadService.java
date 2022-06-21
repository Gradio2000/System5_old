package com.example.qtest.service;

import com.example.qtest.model.Answer;
import com.example.qtest.model.Question;
import com.example.qtest.model.Test;
import com.example.qtest.repository.TestReposytory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
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

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));

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
            questionList.add(question);
        }
        reader.close();
        Test test = testReposytory.findById(testId).orElse(null);
        assert test != null;
        test.setQuestions(questionList);
        testReposytory.save(test);
    }
}