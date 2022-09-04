package com.example.qtest.controller;

import com.example.qtest.dto.AppointTestDto;
import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.model.AppointTest;
import com.example.qtest.model.AppointTestAmount;
import com.example.qtest.model.Test;
import com.example.qtest.repository.AppointTestAmountRepository;
import com.example.qtest.repository.AppointTestRepository;
import com.example.qtest.repository.GroupTestRepository;
import com.example.qtest.repository.TestReposytory;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exam")
@PreAuthorize("hasRole('ADMIN_TEST')")
@Slf4j
public class ExamController {
    private final AppointTestRepository appointTestRepository;
    private final UserRepository userRepository;
    private final GroupTestRepository groupTestRepository;
    private final DtoUtils dtoUtils;
    private final TestReposytory testReposytory;
    private final AppointTestAmountRepository appointTestAmountRepository;



    public ExamController(AppointTestRepository appointTestRepository, UserRepository userRepository,
                          GroupTestRepository groupTestRepository, DtoUtils dtoUtils,
                          TestReposytory testReposytory, AppointTestAmountRepository appointTestAmountRepository) {
        this.appointTestRepository = appointTestRepository;
        this.userRepository = userRepository;
        this.groupTestRepository = groupTestRepository;
        this.dtoUtils = dtoUtils;
        this.testReposytory = testReposytory;
        this.appointTestAmountRepository = appointTestAmountRepository;
    }

    @GetMapping("/exam")
    public String appointExam(@AuthenticationPrincipal AuthUser authUser,
                              Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        List<GroupTestDto> groupTestDtoList = groupTestRepository.findAll().stream()
                .map(dtoUtils::convertToGroupTestDtoWithTestName)
                .collect(Collectors.toList());
        model.addAttribute("groupTestDtoList", groupTestDtoList);

        List<UserDto> userDtoList = userRepository.findAll().stream()
                .map(UserDto::getInstance)
                .collect(Collectors.toList());
        model.addAttribute("userDtoList", userDtoList);
        return "qtest/appointExam";
    }

    @GetMapping("/getUsersAppoints")
    @ResponseBody
    public List<AppointTest> getUsersAppoints(@RequestParam Integer userId, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        return appointTestRepository.findAllByUser(userRepository.findById(userId).orElse(null))
                .stream()
                .filter(appointTest -> !appointTest.getFinished())
                .collect(Collectors.toList());
    }

    @PostMapping("/appointExam")
    @ResponseBody
    public HttpStatus appointExam(@RequestParam Integer[] testIds,
                                  @RequestParam Integer[] quesAmounts,
                                  @RequestParam Integer userId,
                                  @RequestParam (required = false) String baseDocName,
                                  @RequestParam (required = false) String consolidTestName,
                                  @RequestParam (required = false) Boolean eko,
                                  @RequestParam (required = false) Integer criteria,
                                  @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        User user = userRepository.findById(userId).orElse(null);
        Map<Integer, Integer> quesAmountAndTestMap = new HashMap<>();
        int amountQues = 0;
        for (int i = 0; i < testIds.length; i++) {
            amountQues = +quesAmounts[i];
            quesAmountAndTestMap.put(testIds[i], quesAmounts[i]);
        }

        AppointTest appointTest = new AppointTest();
        appointTest.setUser(user);
        appointTest.setBase(baseDocName);
        if (quesAmountAndTestMap.size() > 1){
            appointTest.setTestName(consolidTestName);
        }
        else if (quesAmountAndTestMap.size() == 1){
            Test test = testReposytory.findById(testIds[0]).orElse(null);
            assert test != null;
            appointTest.setTestName(test.getTestName());
        }
        appointTest.setAmountQues(amountQues);
        appointTest.setFinished(false);
        appointTest.setEko(eko);
        if (criteria == null){
            appointTest.setCriteria(0);
        }
        else {
            appointTest.setCriteria(criteria);
        }
        appointTestRepository.save(appointTest);

        List<AppointTestAmount> appointTestAmountList = new ArrayList<>();
        for (Integer key: quesAmountAndTestMap.keySet()) {
            AppointTestAmount appointTestAmount = new AppointTestAmount();
            appointTestAmount.setAppointId(appointTest.getId());
            appointTestAmount.setTestId(key);
            appointTestAmount.setQuesAmount(quesAmountAndTestMap.get(key));
            appointTestAmountList.add(appointTestAmount);
        }

        appointTestAmountRepository.saveAll(appointTestAmountList);

        return HttpStatus.OK;
    }

    @GetMapping ("/journal")
    public String getJournal(@AuthenticationPrincipal AuthUser authUser, Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        List<AppointTest> appointTestList = appointTestRepository.findAllByFinishedAndEko(true, true);
        List<AppointTestDto> appointTestDtoList = dtoUtils.convertToAppointTestDtoList(appointTestList);
        model.addAttribute("appointTestDtoList", appointTestDtoList);
        return "qtest/journalEko";
    }

    @GetMapping("/journalBase")
    public String getJournalBase(@AuthenticationPrincipal AuthUser authUser, Model model,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam (defaultValue = "10") Integer size,
                                 @RequestParam (defaultValue = "up") String sort){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));

        Pageable pageable;
        if (sort.equals("down")){
            pageable = PageRequest.of(page, size, Sort.by("attempttest.dateTime").descending());
        }
        else {
            pageable = PageRequest.of(page, size, Sort.by("attempttest.dateTime").ascending());
        }

        Page<AppointTest> attemptsList = appointTestRepository.findAll(pageable);
        model.addAttribute("attemptsList", attemptsList);
        model.addAttribute("sort", sort);
        return "qtest/journalBase";
    }

    @PostMapping("/deleteAppoint")
    @ResponseBody
    public HttpStatus deleteAppoint(@RequestParam Integer appointId){
        appointTestRepository.deleteById(appointId);
        appointTestAmountRepository.deleteAllByAppointId(appointId);
        return HttpStatus.OK;
    }
}
