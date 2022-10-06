package com.example.search.controlller;

import com.example.search.model.Search;
import com.example.search.repository.SearchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchController {

    private SearchRepository searchRepository;

    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @GetMapping("/search")
    public String search(){
        return "/search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    public HttpStatus searchPost(String value){
        List<Search> searches = searchRepository.getAdress(value.toUpperCase());
        return HttpStatus.OK;
    }
}
