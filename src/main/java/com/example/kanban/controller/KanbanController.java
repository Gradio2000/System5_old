package com.example.kanban.controller;

import com.example.kanban.dto.KanbanDto;
import com.example.kanban.model.Kanban;
import com.example.kanban.repository.KanbanRepository;
import com.example.system5.dto.UserDto;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/kanban")
public class KanbanController {
    private final KanbanRepository kanbanRepository;

    public KanbanController(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
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
}
