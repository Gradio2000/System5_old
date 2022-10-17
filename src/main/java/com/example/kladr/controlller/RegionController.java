package com.example.kladr.controlller;

import com.example.kladr.model.KladrAll;
import com.example.kladr.repository.KladrAllRepository;
import com.example.kladr.repository.KladrRepository;
import com.example.kladr.repository.RegionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RegionController {
    private final RegionRepository regionRepository;
    private final KladrRepository kladrRepository;
    private final KladrAllRepository kladrAllRepository;

    public RegionController(RegionRepository regionRepository, KladrRepository kladrRepository, KladrAllRepository kladrAllRepository) {
        this.regionRepository = regionRepository;
        this.kladrRepository = kladrRepository;
        this.kladrAllRepository = kladrAllRepository;
    }

    @GetMapping("/search")
    public String getSearchPage(){
        return "search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    public List<KladrAll> getRegion(String value){
//        List<Region> regions = regionRepository.getRegion(value);
//        List<Kladr> kladrList = kladrRepository.findAllByNameContainingIgnoreCase(value);
        List<KladrAll> kladrList = kladrAllRepository.findAllByNameContainingIgnoreCase(value, PageRequest.of(0,10));
        return kladrList;
    }
}
