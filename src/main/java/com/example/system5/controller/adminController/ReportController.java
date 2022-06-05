package com.example.system5.controller.adminController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Month;
import com.example.system5.model.System5;
import com.example.system5.model.User;
import com.example.system5.repository.System5Repository;
import com.example.system5.repository.UserRepository;
import com.example.system5.service.system5Service.UserListTransformer;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@Slf4j
public class ReportController {
    private final System5Repository system5Repository;
    private final UserRepository userRepository;
    private final UserListTransformer userListTransformer;

    public ReportController(System5Repository system5Repository, UserRepository userRepository,
                            UserListTransformer userListTransformer) {
        this.system5Repository = system5Repository;
        this.userRepository = userRepository;
        this.userListTransformer = userListTransformer;
    }

    @GetMapping("/prepareReport")
    public String getMonths(@AuthenticationPrincipal AuthUser authUser, Model model) {
        List<String> months = Arrays.stream(Month.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("months", months);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "prepareReport";
    }

    @GetMapping("/prepareHalfYearReport")
    public String prepareHalfYearReport(@AuthenticationPrincipal AuthUser authUser, Model model) {
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "prepareHalfYearReport";
    }

    @GetMapping("/report")
    public String getMonthReport(@AuthenticationPrincipal AuthUser authUser,
                                 @RequestParam String month,
                                 Model model) {

        List<System5> system5List = system5Repository.findAllByMonth(month);
        model.addAttribute(system5List);
        model.addAttribute("month", month);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "report";
    }

    @GetMapping("/halfYearReport")
    public String getHalfYearReport(@AuthenticationPrincipal AuthUser authUser,
                                    @RequestParam Integer half,
                                    Model model) {

        List<User> userList = userRepository.findAll().stream()
                .filter(user -> user.getSystem5List().size() != 0)
                .collect(Collectors.toList());

        Map<UserDto, String[]> userDtoMap = new HashMap<>();
        if(half == 1) {
            for (User user : userList) {
                List<System5> system5List = user.getSystem5List();
                system5List.removeIf(system5 -> !userListTransformer.getFirstHalf(system5.getMonth()));
            }
             userDtoMap = userListTransformer.getUserDtoListMapForFirstHalf(userList);
        }
        else if (half == 2){
            for (User user : userList) {
                List<System5> system5List = user.getSystem5List();
                system5List.removeIf(system5 -> !userListTransformer.getSecondHalf(system5.getMonth()));
            }
            userDtoMap = userListTransformer.getUserDtoListMapForSecondHalf(userList);
        }

        model.addAttribute("modelMap", userDtoMap);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("half", half);
        return "reportHalfYear";
    }

    @GetMapping("/prepareYearReport")
    public String prepareYearReport(@AuthenticationPrincipal AuthUser authUser,
                                    Model model){
        List<User> userList = userRepository.findAll().stream()
                .filter(user -> user.getSystem5List().size() != 0)
                .collect(Collectors.toList());

        Map<UserDto, String[]> userDtoMap = new HashMap<>();
        userDtoMap = userListTransformer.getUserDtoListMapForYear(userList);
        model.addAttribute("modelMap", userDtoMap);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "reportYear";
    }
}
