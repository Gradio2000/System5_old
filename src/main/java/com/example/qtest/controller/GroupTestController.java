package com.example.qtest.controller;

import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.model.GroupTest;
import com.example.qtest.repository.GroupTestRepository;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/testGroup")
public class GroupTestController {

    private final GroupTestRepository groupTestRepository;
    private final DtoUtils dtoUtils;

    public GroupTestController(GroupTestRepository groupTestRepository, DtoUtils dtoUtils) {
        this.groupTestRepository = groupTestRepository;
        this.dtoUtils = dtoUtils;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN_TEST')")
    public String testGroupList(@AuthenticationPrincipal AuthUser authUser,
                                Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        List<GroupTestDto> groupTests = groupTestRepository.findByOrderByGrouptestId().stream()
                .map(dtoUtils::convertToGroupTestDto)
                .collect(Collectors.toList());
        model.addAttribute("groupTests", groupTests);
        return "qtest/administration/testGroupList";
    }

    @PostMapping("/add")
    public String addGroupTest(@ModelAttribute GroupTest groupTest){
        if (groupTest.getName().isEmpty()){
            return "redirect:/testGroup/list?error=200";
        }
        groupTestRepository.save(groupTest);
        return "redirect:/testGroup/list";
    }

    @PostMapping("/delete")
    public String deleteGroupTest(@RequestParam (required = false) Integer[] check){
        try {
            groupTestRepository.deleteAllById(Arrays.asList(check));
        } catch (NullPointerException e) {
            return "redirect:/testGroup/list?error=100";
        }
        return "redirect:/testGroup/list";
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public HttpStatus editGroupTestName(@PathVariable Integer id, GroupTestDto groupTestDto){
        GroupTest groupTest = groupTestRepository.findById(id).orElse(null);
        assert groupTest != null;
        groupTest.setName(groupTestDto.getName());
        groupTestRepository.save(groupTest);
        return HttpStatus.OK;
    }

    @GetMapping("/listForTesting")
    public String getAllTestsForTesting(@AuthenticationPrincipal AuthUser authUser,
                                        Model model){
        List<GroupTestDto> groupTests = groupTestRepository.findByOrderByGrouptestId().stream()
                .map(dtoUtils::convertToGroupTestDto)
                .collect(Collectors.toList());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        model.addAttribute("groupTests", groupTests);
        return "qtest/forTesting/allTestGroupForTesting";
    }
}
