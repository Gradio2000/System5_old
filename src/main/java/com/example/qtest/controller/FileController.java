package com.example.qtest.controller;

import com.example.qtest.service.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/fileUpload")
    public String loadFile(@RequestParam MultipartFile file, @RequestParam Integer testId) {
        try {
            fileUploadService.uploadQuestionFromFile(file, testId);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/tests/" + testId + "/questions?error=100";
        }
        return "redirect:/tests/" + testId + "/questions";
    }
}
