package com.example.kladr.controlller;

import com.example.dto.HouseDto;
import com.example.kladr.model.House;
import com.example.kladr.model.KladrAll;
import com.example.kladr.model.Street;
import com.example.kladr.repository.HouseRepository;
import com.example.kladr.repository.KladrAllRepository;
import com.example.kladr.repository.StreetRepository;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegionController {

    private final KladrAllRepository kladrAllRepository;
    private final StreetRepository streetRepository;

    private final HouseRepository houseRepository;

    public RegionController(KladrAllRepository kladrAllRepository,
                            StreetRepository streetRepository, HouseRepository houseRepository) {
        this.kladrAllRepository = kladrAllRepository;
        this.streetRepository = streetRepository;
        this.houseRepository = houseRepository;
    }

    @GetMapping("/search")
    public String getSearchPage(@AuthenticationPrincipal AuthUser authUser, Model model){
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    @Cacheable("regions")
    public List<KladrAll> getRegion(String value){
        String[] mass = value.split(" ");
        String value1 = "";
        String value2 = "";
        String value3 = "";
        if (mass.length == 1){
            return kladrAllRepository.selectAll(mass[0]);
        }
        if (mass.length == 2){
            value1 = mass[0];
            value2 = mass[1];
            return kladrAllRepository.selectAll(value1, value2);
        }
        if (mass.length == 3){
            value1 = mass[0];
            value2 = mass[1];
            value3 = mass[2];
            return kladrAllRepository.selectAll(value1, value2, value3);
        }
        else {
            return null;
        }
    }

    @PostMapping("/searchStreet")
    @ResponseBody
    public List<Street> getStreetList(@RequestParam Integer regCode,
                                      @RequestParam Integer areaCode,
                                      @RequestParam Integer cityCode,
                                      @RequestParam Integer punktCode){
            List<Street> streetList = streetRepository.findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCode(
                    regCode, areaCode, cityCode, punktCode, PageRequest.of(0, 10));
        return streetList;
    }

    @PostMapping("/findStreet")
    @ResponseBody
    public List<Street> findStreet(@RequestParam Integer regCodeId,
                                   @RequestParam Integer areaCodeId,
                                   @RequestParam Integer cityCodeId,
                                   @RequestParam Integer punktCodeId,
                                   @RequestParam String value){
       List<Street> streetList = streetRepository.getStreet(regCodeId, areaCodeId, cityCodeId, punktCodeId, value);
       return streetList;
    }

    @PostMapping("/searchHouse")
    @ResponseBody
    public List<HouseDto> getHouseList (@RequestParam Integer regCodeId,
                                        @RequestParam Integer areaCodeId,
                                        @RequestParam Integer cityCodeId,
                                        @RequestParam Integer punktCodeId,
                                        @RequestParam Integer streetCodeId){
        List<House> houseList =
                houseRepository.findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCodeAndStreetCode(
                        regCodeId, areaCodeId, cityCodeId, punktCodeId, streetCodeId, PageRequest.of(0, 10));
        List<HouseDto> houseDtoList = new ArrayList<>();
        int id = 1;
        for (House house: houseList){
            String[] houseMass = house.getName().split(",");
            for (String mass : houseMass) {
                HouseDto houseDto = HouseDto.getInstance(id, mass, house.getIndex());
                houseDtoList.add(houseDto);
                id++;
            }
        }
        return houseDtoList.stream().limit(10).collect(Collectors.toList());
    }

    @PostMapping("/findHouse")
    @ResponseBody
    public List<HouseDto> findHouse (@RequestParam Integer regCodeId,
                                   @RequestParam Integer areaCodeId,
                                   @RequestParam Integer cityCodeId,
                                   @RequestParam Integer punktCodeId,
                                   @RequestParam Integer streetCodeId,
                                   @RequestParam String value){
        List<House> houseList =
                houseRepository.getHouses(
                        regCodeId, areaCodeId, cityCodeId, punktCodeId, streetCodeId, value);
        List<HouseDto> houseDtoList = new ArrayList<>();
        int id = 1;
        for (House house: houseList){
            String[] houseMass = house.getName().split(",");
            for (String mass : houseMass) {
                HouseDto houseDto = HouseDto.getInstance(id, mass, house.getIndex());
                houseDtoList.add(houseDto);
                id++;
            }
        }

        return houseDtoList.stream().
                filter(houseDto -> houseDto.getName().contains(value))
                .limit(10)
                .collect(Collectors.toList());
    }
}
