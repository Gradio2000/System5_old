package com.example.system5.service.system5Service;

import com.example.system5.dto.UserDto;
import com.example.system5.model.Month;
import com.example.system5.model.System5;
import com.example.system5.model.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserListTransformer {
    private final GetTotalMarkService getTotalMarkService;

    public UserListTransformer(GetTotalMarkService getTotalMarkService) {
        this.getTotalMarkService = getTotalMarkService;
    }

    public boolean getFirstHalf(String newMonth){
        return Arrays.stream(Month.values())
                .filter(month -> month.ordinal() <= 5)
                .map(Enum::name)
                .collect(Collectors.toList()).contains(newMonth);
    }

    public boolean getSecondHalf(String newMonth){
        return Arrays.stream(Month.values())
                .filter(month -> month.ordinal() > 5)
                .map(Enum::name)
                .collect(Collectors.toList()).contains(newMonth);
    }

    public Map<UserDto, String[]> getUserDtoListMapForFirstHalf(List<User> userList){
        Map<UserDto, String[]> userDtoListMap = new HashMap<>();
        for (User user : userList) {
            UserDto userDto = UserDto.getInstance(user);
            String[] strings = {"", "", "", "", "", ""};
            List<System5> system5List = user.getSystem5List();
            for (System5 system5 : system5List) {
                switch (system5.getMonth()) {
                    case ("ЯНВАРЬ"):
                        strings[0] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ФЕВРАЛЬ"):
                        strings[1] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("МАРТ"):
                        strings[2] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("АПРЕЛЬ"):
                        strings[3] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("МАЙ"):
                        strings[4] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ИЮНЬ"):
                        strings[5] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                }
            }
            String[] newStrings = new String[7];
            System.arraycopy(strings, 0, newStrings, 0, strings.length);
            newStrings[6] = getTotalMarkService.getTotalHalfYearProcess(strings);
            userDtoListMap.put(userDto, newStrings);
        }
        return userDtoListMap;
    }

    public Map<UserDto, String[]> getUserDtoListMapForSecondHalf(List<User> userList){
        Map<UserDto, String[]> userDtoListMap = new HashMap<>();
        for (User user : userList) {
            UserDto userDto = UserDto.getInstance(user);
            String[] strings = {"", "", "", "", "", ""};
            List<System5> system5List = user.getSystem5List();
            for (System5 system5 : system5List) {
                switch (system5.getMonth()) {
                    case ("ИЮЛЬ"):
                        strings[0] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("АВГУСТ"):
                        strings[1] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("СЕНТЯБРЬ"):
                        strings[2] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ОКТЯБРЬ"):
                        strings[3] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("НОЯБРЬ"):
                        strings[4] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ДЕКАБРЬ"):
                        strings[5] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                }
            }
            String[] newStrings = new String[7];
            System.arraycopy(strings, 0, newStrings, 0, strings.length);
            newStrings[6] = getTotalMarkService.getTotalHalfYearProcess(strings);
            userDtoListMap.put(userDto, newStrings);
        }
        return userDtoListMap;
    }

    public Map<UserDto, String[]> getUserDtoListMapForYear(List<User> userList){
        Map<UserDto, String[]> userDtoListMap = new HashMap<>();
        for (User user : userList) {
            UserDto userDto = UserDto.getInstance(user);
            String[] strings = new String[12];
            for (int i = 0; i < strings.length; i++) {
                strings[i] = "";
            }
            List<System5> system5List = user.getSystem5List();
            for (System5 system5 : system5List) {
                switch (system5.getMonth()) {
                    case ("ЯНВАРЬ"):
                        strings[0] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ФЕВРАЛЬ"):
                        strings[1] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("МАРТ"):
                        strings[2] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("АПРЕЛЬ"):
                        strings[3] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("МАЙ"):
                        strings[4] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ИЮНЬ"):
                        strings[5] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ИЮЛЬ"):
                        strings[6] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("АВГУСТ"):
                        strings[7] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("СЕНТЯБРЬ"):
                        strings[8] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ОКТЯБРЬ"):
                        strings[9] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("НОЯБРЬ"):
                        strings[10] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                    case ("ДЕКАБРЬ"):
                        strings[11] = system5.getTotalMark5().getTotalMarkEmpl();
                        break;
                }
            }

            String[] newStrings = new String[13];
            System.arraycopy(strings, 0, newStrings, 0, strings.length);
            newStrings[12] = getTotalMarkService.getTotalYearProcess(strings);
            userDtoListMap.put(userDto, newStrings);
        }
        return userDtoListMap;
    }

}
