package com.example.search.controlller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    @GetMapping("/search")
    public String search(){
        return "/search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    public HttpStatus searchPost(){
        return HttpStatus.OK;
    }
}
