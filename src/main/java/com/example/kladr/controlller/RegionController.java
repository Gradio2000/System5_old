package com.example.kladr.controlller;

import com.example.kladr.model.Region;
import com.example.kladr.repository.RegionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RegionController {
    private final RegionRepository regionRepository;

    public RegionController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping("/search")
    public String getSearchPage(){
        return "search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    public List<Region> getRegion(String value){
        List<Region> regions = regionRepository.getRegion(value);
        return regions;
    }
}
