package com.example.qtest.controller;

import com.example.qtest.dto.AppointTestDto;
import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.dto.TestDto;
import com.example.qtest.model.AppointTest;
import com.example.qtest.model.Test;
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

import java.util.List;
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



    public ExamController(AppointTestRepository appointTestRepository, UserRepository userRepository,
                          GroupTestRepository groupTestRepository, DtoUtils dtoUtils,
                          TestReposytory testReposytory) {
        this.appointTestRepository = appointTestRepository;
        this.userRepository = userRepository;
        this.groupTestRepository = groupTestRepository;
        this.dtoUtils = dtoUtils;
        this.testReposytory = testReposytory;
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
    public List<TestDto> getUsersAppoints(@RequestParam Integer userId, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());

        List<Test> testList = appointTestRepository
                .findAllByUser(userRepository.findById(userId).orElse(null))
                .stream()
                .filter(appointTest -> !appointTest.getFinished())
                .map(AppointTest::getTest)
                .collect(Collectors.toList());

        return DtoUtils.convertToListDto(testList);
    }

    @PostMapping("/appointExam")
    @ResponseBody
    public HttpStatus appointExam(@RequestParam Integer testId, @RequestParam Integer userId,
                                  @RequestParam (required = false) String base, @RequestParam (required = false) Boolean eko,
                                  @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        User user = userRepository.findById(userId).orElse(null);
        Test test = testReposytory.findById(testId).orElse(null);
        AppointTest appointTest = appointTestRepository.findByUserAndTestAndFinished(user, test, false);
        if (appointTest == null){
            appointTest = new AppointTest(user, test, false, base, eko);
            appointTestRepository.save(appointTest);
        }
        else {
            appointTestRepository.deleteByUserAndTest(userId, testId);
        }
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
}
