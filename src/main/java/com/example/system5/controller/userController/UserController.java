package com.example.system5.controller.userController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Position;
import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/my_employers")
    public String getEmployersList(@AuthenticationPrincipal AuthUser authUser, Model model){
        User user = authUser.getUser();
        List<Position> positionList = null;
        try {
            positionList = user.getPosition().getEmployersList();
        } catch (Exception e) {
            return "employers";
        }
        model.addAttribute("positionList", positionList);
        model.addAttribute("user", UserDto.getInstance(user));
        return "employers";
    }

    @GetMapping("/getUser")
    public String getUser(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "/userEdit";
    }

    @PostMapping("/editUser")
    public String editUser (@ModelAttribute User user, @AuthenticationPrincipal AuthUser authUser,
                            HttpServletRequest request){
        User userForEdit = userRepository.getUserByUserId(authUser.getUser().getUserId());
        userForEdit.setName(user.getName());
        userForEdit.setLogin(user.getLogin());
        userRepository.save(userForEdit);
        authUser.setUser(userForEdit);
        return "redirect:/list";
    }
}
