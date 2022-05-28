package com.example.system5.controller.userController;

import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {

    @GetMapping("/accessDenied")
    public String accessDenied(@AuthenticationPrincipal AuthUser authUser,
                               Model model){
        UserDto userDto = UserDto.getInstance(authUser.getUser());
        model.addAttribute("user", userDto);
        return "accessDenied";
    }
}
