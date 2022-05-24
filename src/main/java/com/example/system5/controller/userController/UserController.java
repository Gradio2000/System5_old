package com.example.system5.controller.userController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Position;
import com.example.system5.model.User;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @GetMapping(value = "/my_employers")
    public String getEmployersList(@AuthenticationPrincipal AuthUser authUser, Model model){
        User user = authUser.getUser();
        List<Position> positionList = null;
        try {
            positionList = user.getPosition().getEmployersList();
        } catch (Exception e) {
            return "employers";
        }
        model.addAttribute(positionList);
        model.addAttribute("user", UserDto.getInstance(user));
        return "employers";
    }

}
