package com.example.system5.controller.adminController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Month;
import com.example.system5.model.System5;
import com.example.system5.model.User;
import com.example.system5.repository.System5Repository;
import com.example.system5.repository.UserRepository;
import com.example.system5.service.system5Service.GetTotalMarkService;
import com.example.system5.service.system5Service.SaveOrUpdateSystem5Service;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ArchiveController {
    private final UserRepository userRepository;
    private final System5Repository system5Repository;
    private final SaveOrUpdateSystem5Service saveOrUpdateSystem5Service;

    public ArchiveController(UserRepository userRepository,
                             System5Repository system5Repository,
                             SaveOrUpdateSystem5Service saveOrUpdateSystem5Service) {
        this.userRepository = userRepository;
        this.system5Repository = system5Repository;
        this.saveOrUpdateSystem5Service = saveOrUpdateSystem5Service;
    }

    @GetMapping("/archive")
    public String getEmployersList(@AuthenticationPrincipal AuthUser authUser,
                                   Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("userList", userRepository.findAll());
        return "employersListForGetArchive";
    }

    @GetMapping("/getUserSystem5Archive/{id}")
    public String getUserListSystem5(@AuthenticationPrincipal AuthUser authUser, @PathVariable Integer id,
                                     Model model, @ModelAttribute @Valid System5 system5,
                                     BindingResult bindingResult){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        User user = userRepository.findById(id).orElse(null);

        List<String> monthList = Arrays.stream(Month.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("monthList", monthList);

        assert user != null;
        model.addAttribute("system5List", system5Repository.findAllByUserId(user.getUserId()));
        model.addAttribute("userId", id);
        return "ArchiveUserListSystem5";
    }

    @PostMapping("/addFromAdminModule")
    public String addMarkFromAdminModule(@ModelAttribute @Valid System5 system5,
                                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "redirect:/admin/getUserSystem5Archive/" + system5.getUserId() + "?error=1";
        }

        GetTotalMarkService.toUpperCase(system5);

        System5 systemForUpdate = system5Repository.findByMonthAndUserId(system5.getMonth(), system5.getUserId());
        if (systemForUpdate != null){
            saveOrUpdateSystem5Service.updateSystem5(systemForUpdate, system5);
        }
        else {
            system5.setUserId(system5.getUserId());
            saveOrUpdateSystem5Service.saveSystem5(system5);
        }

        return "redirect:/admin/getUserSystem5Archive/" + system5.getUserId();
    }
}
