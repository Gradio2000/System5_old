package com.example.kanban.controller;

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

import java.util.List;

@Controller
@RequestMapping("/kanban")
public class KanbanController {
    private final KanbanRepository kanbanRepository;

    public KanbanController(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @GetMapping("/kanban")
    public String getAllKanban(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        List<Kanban> kanbanList = kanbanRepository.findAll(Sort.by("id"));
        model.addAttribute("kanbanList", kanbanList);
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
}
