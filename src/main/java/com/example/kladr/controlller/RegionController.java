package com.example.kladr.controlller;

import com.example.kladr.model.Kladr;
import com.example.kladr.repository.KladrRepository;
import com.example.kladr.repository.RegionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RegionController {
    private final RegionRepository regionRepository;
    private final KladrRepository kladrRepository;

    public RegionController(RegionRepository regionRepository, KladrRepository kladrRepository) {
        this.regionRepository = regionRepository;
        this.kladrRepository = kladrRepository;
    }

    @GetMapping("/search")
    public String getSearchPage(){
        return "search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    public List<Kladr> getRegion(String value){
//        List<Region> regions = regionRepository.getRegion(value);
        List<Kladr> kladrList = kladrRepository.findAllByNameContainingIgnoreCase(value);
        return kladrList;
    }
}
