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

import java.time.LocalDate;
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
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        List<String> months = Arrays.stream(Month.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("months", months);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "sys5pages/prepareReport";
    }

    @GetMapping("/prepareHalfYearReport")
    public String prepareHalfYearReport(@AuthenticationPrincipal AuthUser authUser, Model model) {
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("years", getYear());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "sys5pages/prepareHalfYearReport";
    }

    @GetMapping("/report")
    public String getMonthReport(@AuthenticationPrincipal AuthUser authUser,
                                 @RequestParam String month,
                                 Model model) {

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        List<System5> system5List = system5Repository.findAllByMonth(month);
        model.addAttribute(system5List);
        model.addAttribute("month", month);
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "sys5pages/report";
    }

    @GetMapping("/halfYearReport")
    public String getHalfYearReport(@AuthenticationPrincipal AuthUser authUser,
                                    @RequestParam Integer half,
                                    @RequestParam (required = false) Integer year,
                                    Model model) {

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        if (year == null){
            year = LocalDate.now().getYear();
        }
        Integer finalYear = year;

        List<User> userList = userRepository.findAll().stream()
                .filter(user -> user.getSystem5List().size() != 0)
                .collect(Collectors.toList());

        Map<UserDto, String[]> userDtoMap = new HashMap<>();
        if(half == 1) {
             userDtoMap = userListTransformer.getUserDtoListMapForFirstHalf(userList, finalYear);
        }
        else if (half == 2){
            userDtoMap = userListTransformer.getUserDtoListMapForSecondHalf(userList, finalYear);
        }

        model.addAttribute("modelMap", userDtoMap);
        model.addAttribute("half", half);
        model.addAttribute("selectedYear", year);
        return "sys5pages/reportHalfYear";
    }

    @GetMapping("/prepareYearReport")
    public String prepareYearReport(@AuthenticationPrincipal AuthUser authUser,
                                    Model model, @RequestParam (required = false) Integer year){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        if (year == null){
            year = LocalDate.now().getYear();
        }
        model.addAttribute("selectedYear", year);
        model.addAttribute("years", getYear());


        List<User> userList = userRepository.findAll().stream()
                .filter(user -> user.getSystem5List().size() != 0)
                .collect(Collectors.toList());

        model.addAttribute("modelMap", userListTransformer.getUserDtoListMapForYear(userList, year));
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "sys5pages/reportYear";
    }

    public List<Integer> getYear(){
        return system5Repository.findAll().stream()
                .map(System5::getYear)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
