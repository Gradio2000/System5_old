package com.example.kanban.controller;

import com.example.kanban.dto.KanbanDto;
import com.example.kanban.model.Kanban;
import com.example.kanban.repository.KanbanRepository;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDto;
import com.example.system5.dto.UserDtoNameOnly;
import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/kanban")
public class KanbanController {
    private final KanbanRepository kanbanRepository;
    private final UserRepository userRepository;


    public KanbanController(KanbanRepository kanbanRepository, UserRepository userRepository) {
        this.kanbanRepository = kanbanRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/kanban")
    public String getAllKanban(@AuthenticationPrincipal AuthUser authUser,
                               Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        List<KanbanDto> kanbanList = kanbanRepository.findAll(Sort.by("id")).stream()
                .map(KanbanDto::getInstance)
                .collect(Collectors.toList());
        model.addAttribute("kanbanList", kanbanList);
        model.addAttribute("kanban", new Kanban());
        return "kanban/kanban";
    }

    @PostMapping("/move")
    @ResponseBody
    public HttpStatus moveKanbanTask(@RequestParam String columnId, @RequestParam Integer kanbanId){
        Kanban kanban = kanbanRepository.findById(kanbanId).orElse(null);
        assert kanban != null;
        switch (columnId){
            case ("col1"):
                kanban.setStarted(true);
                kanban.setContinues(false);
                kanban.setFinished(false);
                kanbanRepository.save(kanban);
                break;
            case ("col2"):
                kanban.setStarted(false);
                kanban.setContinues(true);
                kanban.setFinished(false);
                kanbanRepository.save(kanban);
                break;
            case ("col3"):
                kanban.setStarted(false);
                kanban.setContinues(false);
                kanban.setFinished(true);
                kanbanRepository.save(kanban);
                break;
        }
        return HttpStatus.OK;
    }

    @PostMapping("/addNewKanban")
    public String addNewCanban(@ModelAttribute Kanban kanban,
                               @AuthenticationPrincipal AuthUser authUser,
                               @RequestParam String date){
        kanban.setUser(authUser.getUser());
        Date date1 = java.sql.Date.valueOf(LocalDate.parse(date));
        kanban.setTaskEndDate(date1);
        kanbanRepository.save(kanban);
        return "redirect:kanban";
    }

    @PostMapping("/delete")
    @ResponseBody
    public HttpStatus deleteKanban(@RequestParam Integer kanbanId){
        kanbanRepository.deleteById(kanbanId);
        return HttpStatus.OK;
    }

    @GetMapping("getKanban")
    @ResponseBody
    public KanbanDto getKanbanById(@RequestParam Integer kanId){
        return KanbanDto.getInstance(Objects.requireNonNull(kanbanRepository.findById(kanId).orElse(null)));
    }

    @PostMapping("/editName")
    @ResponseBody
    public HttpStatus editName(@RequestParam String kanbanName, @RequestParam Integer kanbanId){
        Kanban kanban = kanbanRepository.findById(kanbanId).orElse(null);
        assert kanban != null;
        kanban.setKanbanName(kanbanName);
        kanbanRepository.save(kanban);
        return HttpStatus.OK;
    }

    @PostMapping("/editDescribe")
    @ResponseBody
    public HttpStatus editDescribe(@RequestParam String describe, @RequestParam Integer kanbanId){
        Kanban kanban = kanbanRepository.findById(kanbanId).orElse(null);
        assert kanban != null;
        kanban.setDescribe(describe);
        kanbanRepository.save(kanban);
        return HttpStatus.OK;
    }


    @PostMapping("/editDate")
    @ResponseBody
    public HttpStatus editDate(@RequestParam String endDate, @RequestParam Integer kanbanId){
        Kanban kanban = kanbanRepository.findById(kanbanId).orElse(null);
        assert kanban != null;
        Date date = java.sql.Date.valueOf(LocalDate.parse(endDate));
        kanban.setTaskEndDate(date);
        kanbanRepository.save(kanban);
        return HttpStatus.OK;
    }

    @GetMapping("/getUserDtoList")
    @ResponseBody
    public List<UserDtoNameOnly> getUserDtoList(@RequestParam Integer kanId, Model model){

        List<Integer> userDtoNameOnlyListIds = Objects.requireNonNull(kanbanRepository
                        .findById(kanId).orElse(null)).getUserList().stream()
                .map(User::getUserId)
                .collect(Collectors.toList());
        List<UserDtoNameOnly> userDtoList = DtoUtils.convertToUserDtoNameOnlyList(userRepository.findAll());
        userDtoList.removeIf(userDtoNameOnly -> userDtoNameOnlyListIds.contains(userDtoNameOnly.getUserId()));

        model.addAttribute("userDtoList", userDtoList);
        return userDtoList;
    }

    @PostMapping("/editMembers")
    @ResponseBody
    public UserDtoNameOnly editMembers(@RequestParam Integer memberSelect, @RequestParam Integer kanbanId){
        User user = userRepository.findById(memberSelect).orElse(null);
        Kanban kanban = kanbanRepository.findById(kanbanId).orElse(null);
        assert kanban != null;
        List<User> userList = kanban.getUserList();
        userList.add(user);
        kanbanRepository.save(kanban);
        assert user != null;
        return UserDtoNameOnly.getInstance(user);
    }
}
