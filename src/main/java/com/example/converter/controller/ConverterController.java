package com.example.converter.controller;

import com.example.converter.service.LoadFile;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ConverterController {
    @GetMapping("/converter")
    public String getconverterPage(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "/converter/converter";
    }

    @PostMapping("/converter/fileUpload")
    public String fileUpload(@RequestParam MultipartFile file,
                           @AuthenticationPrincipal AuthUser authUser){
        try {
            LoadFile.loadFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/converter/converter?error=100";
        }
        return "redirect:/converter/converter?error=0";
    }
}
