package com.example.search.controlller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {
    @GetMapping("/search")
    public HttpStatus search(){
        return HttpStatus.OK;
    }
}
