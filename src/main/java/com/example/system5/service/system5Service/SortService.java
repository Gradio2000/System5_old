package com.example.system5.service.system5Service;

import com.example.system5.model.Month;
import com.example.system5.model.System5;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SortService {
    public List<System5> sortSystem5(List<System5> system5List){
        Month[] monthList = Month.values();
        Map<String, Integer> map = new HashMap<>();
        for (Month month : monthList){
            map.put(month.name(), month.ordinal());
        }

        System5[] sortedSystem5List = new System5[12];
        for (System5 system5 : system5List){
            int index = map.get(system5.getMonth());
            sortedSystem5List[index] = system5;
        }

        return Arrays.stream(sortedSystem5List)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

}
