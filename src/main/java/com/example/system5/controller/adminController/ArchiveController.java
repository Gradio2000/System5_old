package com.example.system5.controller.adminController;

import com.example.system5.dto.UserDto;
import com.example.system5.model.*;
import com.example.system5.repository.System5Repository;
import com.example.system5.repository.UserRepository;
import com.example.system5.service.system5Service.GetTotalMarkService;
import com.example.system5.service.system5Service.SaveOrUpdateSystem5Service;
import com.example.system5.service.system5Service.SortService;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@Slf4j
public class ArchiveController {
    private final UserRepository userRepository;
    private final System5Repository system5Repository;
    private final SaveOrUpdateSystem5Service saveOrUpdateSystem5Service;
    private final GetTotalMarkService getTotalMarkService;
    private final SortService sortService;

    public ArchiveController(UserRepository userRepository, System5Repository system5Repository,
                             SaveOrUpdateSystem5Service saveOrUpdateSystem5Service,
                             GetTotalMarkService getTotalMarkService, SortService sortService) {
        this.userRepository = userRepository;
        this.system5Repository = system5Repository;
        this.saveOrUpdateSystem5Service = saveOrUpdateSystem5Service;
        this.getTotalMarkService = getTotalMarkService;
        this.sortService = sortService;
    }

    @GetMapping("/archive")
    public String getEmployersList(@AuthenticationPrincipal AuthUser authUser,
                                   Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("userList", userRepository.findAll());
        return "sys5pages/employersListForGetArchive";
    }

    @GetMapping("/getUserSystem5Archive/{id}")
    public String getUserListSystem5(@AuthenticationPrincipal AuthUser authUser, @PathVariable Integer id,
                                     Model model, @ModelAttribute @Valid System5 system5,
                                     BindingResult bindingResult,
                                     @ModelAttribute @Valid System5empl system5empl,
                                     BindingResult bindingResult1,
                                     @RequestParam (required = false) Integer year){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        if (year == null){
            year = LocalDate.now().getYear();
        }
        Integer finalYear = year;

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        User user = userRepository.findById(id).orElse(null);

        List<String> monthList = Arrays.stream(Month.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("monthList", monthList);

        assert user != null;
        List<System5> system5List = system5Repository.findAllByUserIdAndYear(user.getUserId(), finalYear);
        model.addAttribute("system5List", sortService.sortSystem5(system5List));


        Map<Integer, Month> monthListFromSystem5List = new HashMap();
        for (System5 system51 : system5List){
            monthListFromSystem5List.put(system51.getSystem5Id(), Month.valueOf(system51.getMonth()));
        }
        model.addAttribute("monthListForEditSystem5Empl", monthListFromSystem5List);


        model.addAttribute("userId", id);
        return "sys5pages/ArchiveUserListSystem5";
    }

    @PostMapping("/addFromAdminModule")
    public String addMarkFromAdminModule(@ModelAttribute @Valid System5 system5,
                                         BindingResult bindingResult, @AuthenticationPrincipal AuthUser authUser){

        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        if (bindingResult.hasErrors()){
            return "redirect:/admin/getUserSystem5Archive/" + system5.getUserId() + "?error=1";
        }

        Integer year = LocalDate.now().getYear();
        system5.setYear(year);

        System5 systemForUpdate = system5Repository.findByMonthAndUserIdAndYear(system5.getMonth(), system5.getUserId(),year);
        if (systemForUpdate != null){
            saveOrUpdateSystem5Service.updateSystem5(systemForUpdate, system5);
        }
        else {
            system5.setUserId(system5.getUserId());
            saveOrUpdateSystem5Service.saveSystem5(system5);
        }

        return "redirect:/admin/getUserSystem5Archive/" + system5.getUserId();
    }


    @PostMapping("/editSystem5EmplFromAdminModule")
    public String editSystem5EmplFromAdminModule(@ModelAttribute @Valid System5empl system5empl,
                                                 BindingResult bindingResult,
                                                 @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        if (bindingResult.hasErrors()){
            return "redirect:/admin/getUserSystem5Archive/" + system5empl.getUser_id() + "?error=1";
        }

        System5 system5 = system5Repository.findById(system5empl.getSystem5Id()).orElse(null);
        assert system5 != null;
        TotalMark5 totalMark5 = system5.getTotalMark5();
        totalMark5.setTotalMarkEmpl(getTotalMarkService.getTotalMarkEmpl(system5empl));

        system5.setTotalMark5(totalMark5);
        system5.setRated(1);
        system5.setSystem5empl(system5empl);

        system5empl.setSystem5(system5);

        system5Repository.save(system5);

        return "redirect:/admin/getUserSystem5Archive/" + system5.getUserId();
    }
}
