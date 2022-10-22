package com.example.kladr.controlller;

import com.example.kladr.dto.HouseDto;
import com.example.kladr.model.House;
import com.example.kladr.model.KladrAll;
import com.example.kladr.model.Street;
import com.example.kladr.repository.HouseRepository;
import com.example.kladr.repository.KladrAllRepository;
import com.example.kladr.repository.StreetRepository;
import com.example.kladr.service.KladrService;
import com.example.system5.dto.UserDto;
import com.example.system5.util.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class RegionController {

    private final KladrAllRepository kladrAllRepository;
    private final StreetRepository streetRepository;

    private final HouseRepository houseRepository;

    private final KladrService kladrService;

    public RegionController(KladrAllRepository kladrAllRepository, StreetRepository streetRepository,
                            HouseRepository houseRepository, KladrService kladrService) {
        this.kladrAllRepository = kladrAllRepository;
        this.streetRepository = streetRepository;
        this.houseRepository = houseRepository;
        this.kladrService = kladrService;
    }

    @GetMapping("/search")
    public String getSearchPage(@AuthenticationPrincipal AuthUser authUser, Model model){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        model.addAttribute("user", UserDto.getInstance(authUser.getUser()));
        return "search/search";
    }

    @PostMapping("/searchPost")
    @ResponseBody
    @Cacheable("regions")
    public List<KladrAll> getRegion(String value, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
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
                                      @RequestParam Integer punktCode,
                                      @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        return streetRepository.findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCode(
                regCode, areaCode, cityCode, punktCode, PageRequest.of(0, 10));
    }

    @PostMapping("/findStreet")
    @ResponseBody
    public List<Street> findStreet(@RequestParam Integer regCodeId,
                                   @RequestParam Integer areaCodeId,
                                   @RequestParam Integer cityCodeId,
                                   @RequestParam Integer punktCodeId,
                                   @RequestParam String value, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        return streetRepository.getStreet(regCodeId, areaCodeId, cityCodeId, punktCodeId, value);
    }

    @PostMapping("/searchHouse")
    @ResponseBody
    public List<HouseDto> getHouseList (@RequestParam Integer regCodeId,
                                        @RequestParam Integer areaCodeId,
                                        @RequestParam Integer cityCodeId,
                                        @RequestParam Integer punktCodeId,
                                        @RequestParam Integer streetCodeId, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        List<House> houseList =
                houseRepository.findAllByRegCodeAndAreaCodeAndCityCodeAndPunktCodeAndStreetCode(
                        regCodeId, areaCodeId, cityCodeId, punktCodeId, streetCodeId, PageRequest.of(0, 10));

        return kladrService.getHouseDtoList(houseList).stream()
                .limit(10)
                .collect(Collectors.toList());
    }

    @PostMapping("/findHouse")
    @ResponseBody
    public List<HouseDto> findHouse(@RequestParam Integer regCodeId,
                                    @RequestParam Integer areaCodeId,
                                    @RequestParam Integer cityCodeId,
                                    @RequestParam Integer punktCodeId,
                                    @RequestParam Integer streetCodeId,
                                    @RequestParam String value, @AuthenticationPrincipal AuthUser authUser){
        log.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                authUser.getUser().getName());
        List<House> houseList =
                houseRepository.getHouses(
                        regCodeId, areaCodeId, cityCodeId, punktCodeId, streetCodeId, value);

        return kladrService.getHouseDtoList(houseList).stream()
                .filter(houseDto -> houseDto.getName().contains(value))
                .limit(10)
                .collect(Collectors.toList());
    }
}
