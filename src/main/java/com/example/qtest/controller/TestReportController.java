package com.example.qtest.controller;

import com.example.qtest.repository.AttemptestReporitory;
import com.example.system5.util.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("qtest/report")
public class TestReportController {
    private final AttemptestReporitory attemptestReporitory;

    public TestReportController(AttemptestReporitory attemptestReporitory) {
        this.attemptestReporitory = attemptestReporitory;
    }

    @GetMapping("/{attemptId}")
    public String getReport(@PathVariable Integer attemptId, @AuthenticationPrincipal AuthUser authUser,
                            Model model){
        model.addAttribute("user", authUser.getUser());
        model.addAttribute("attempt", attemptestReporitory.findById(attemptId).orElse(null));
        return "qtest/report";
    }
}
