package com.example.system5.controller.adminController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.System5;
import com.example.system5.model.User;
import com.example.system5.repository.System5Repository;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ArchiveController {
    private final UserRepository userRepository;
    private final System5Repository system5Repository;

    public ArchiveController(UserRepository userRepository, System5Repository system5Repository) {
        this.userRepository = userRepository;
        this.system5Repository = system5Repository;
    }

    @GetMapping("/archive")
    public String getEmployersList(@AuthenticationPrincipal AuthUser authUser,
                                   Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("userList", userRepository.findAll());
        return "employersListForGetArchive";
    }

    @GetMapping("/{id}")
    public String getUserListSystem5(@AuthenticationPrincipal AuthUser authUser, @PathVariable Integer id,
                                     Model model, @ModelAttribute @Valid System5 system5,
                                     BindingResult bindingResult){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        User user = userRepository.findById(id).orElse(null);

        assert user != null;
        model.addAttribute("system5List", system5Repository.findAllByUserId(user.getUserId()));
        return "ArchveUserListSystem5";
    }
}
