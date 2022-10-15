package com.example.converter.controller;

import com.example.converter.service.ImportExcel;
import com.example.converter.service.LoadFile;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/converter")
public class ConverterController {
    @GetMapping("/converter")
    public String getConverterPage(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("filePath", ImportExcel.getFilename());
        return "/converter/converter";
    }

    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam MultipartFile file, @AuthenticationPrincipal AuthUser authUser,
                             Model model){
        try {
            LoadFile.loadFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/converter/converter?error=100";
        }
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "/converter/download";
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile() throws IOException {

        File file = new File(ImportExcel.getFilename());
        InputStreamResource resource = new InputStreamResource(Files.newInputStream(file.toPath()));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(MediaType.TEXT_PLAIN)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }
}
