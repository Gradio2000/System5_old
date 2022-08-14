package com.example.kanban.controller;

import com.example.kanban.model.Kanban;
import com.example.kanban.repository.KanbanRepository;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class KanbanController {
    private final KanbanRepository kanbanRepository;

    public KanbanController(KanbanRepository kanbanRepository) {
        this.kanbanRepository = kanbanRepository;
    }

    @GetMapping("kanban")
    public String getAllKanban(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        List<Kanban> kanbanList = kanbanRepository.findAll();
        model.addAttribute("kanbanList", kanbanList);
        return "kanban/kanban";
    }
}
