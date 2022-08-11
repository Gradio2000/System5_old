package com.example.qtest.controller;

import com.example.qtest.service.FileUploadService;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class FileController {

    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/fileUpload")
    public String loadFile(@RequestParam MultipartFile file, @RequestParam Integer testId,
                           @AuthenticationPrincipal AuthUser authUser) {
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        try {
            fileUploadService.uploadQuestionFromFile(file, testId);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/tests/" + testId + "/questions?error=100";
        }
        return "redirect:/tests/" + testId + "/questions";
    }
}
