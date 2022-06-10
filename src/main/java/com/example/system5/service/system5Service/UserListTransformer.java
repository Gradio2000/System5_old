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
                String totalMarkEmpl = system5.getTotalMark5().getTotalMarkEmpl() != null ? system5.getTotalMark5().getTotalMarkEmpl() : "";
                switch (system5.getMonth()) {
                    case ("ЯНВАРЬ"):
                        strings[0] = totalMarkEmpl;
                        break;
                    case ("ФЕВРАЛЬ"):
                        strings[1] = totalMarkEmpl;
                        break;
                    case ("МАРТ"):
                        strings[2] = totalMarkEmpl;
                        break;
                    case ("АПРЕЛЬ"):
                        strings[3] = totalMarkEmpl;
                        break;
                    case ("МАЙ"):
                        strings[4] = totalMarkEmpl;
                        break;
                    case ("ИЮНЬ"):
                        strings[5] = totalMarkEmpl;
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
            String[] strings = new String[6];
            Arrays.fill(strings, "");
            List<System5> system5List = user.getSystem5List();
            for (System5 system5 : system5List) {
                String totalMarkEmpl = system5.getTotalMark5().getTotalMarkEmpl() != null ? system5.getTotalMark5().getTotalMarkEmpl() : "";
                switch (system5.getMonth()) {
                    case ("ИЮЛЬ"):
                        strings[0] = totalMarkEmpl;
                        break;
                    case ("АВГУСТ"):
                        strings[1] = totalMarkEmpl;
                        break;
                    case ("СЕНТЯБРЬ"):
                        strings[2] = totalMarkEmpl;
                        break;
                    case ("ОКТЯБРЬ"):
                        strings[3] = totalMarkEmpl;
                        break;
                    case ("НОЯБРЬ"):
                        strings[4] = totalMarkEmpl;
                        break;
                    case ("ДЕКАБРЬ"):
                        strings[5] = totalMarkEmpl;
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
            Arrays.fill(strings, "");
            List<System5> system5List = user.getSystem5List();
            for (System5 system5 : system5List) {
                String totalMarkEmpl = system5.getTotalMark5().getTotalMarkEmpl() != null ? system5.getTotalMark5().getTotalMarkEmpl() : "";
                switch (system5.getMonth()) {
                    case ("ЯНВАРЬ"):
                        strings[0] = totalMarkEmpl;
                        break;
                    case ("ФЕВРАЛЬ"):
                        strings[1] = totalMarkEmpl;
                        break;
                    case ("МАРТ"):
                        strings[2] = totalMarkEmpl;
                        break;
                    case ("АПРЕЛЬ"):
                        strings[3] = totalMarkEmpl;
                        break;
                    case ("МАЙ"):
                        strings[4] = totalMarkEmpl;
                        break;
                    case ("ИЮНЬ"):
                        strings[5] = totalMarkEmpl;
                        break;
                    case ("ИЮЛЬ"):
                        strings[6] = totalMarkEmpl;
                        break;
                    case ("АВГУСТ"):
                        strings[7] = totalMarkEmpl;
                        break;
                    case ("СЕНТЯБРЬ"):
                        strings[8] = totalMarkEmpl;
                        break;
                    case ("ОКТЯБРЬ"):
                        strings[9] = totalMarkEmpl;
                        break;
                    case ("НОЯБРЬ"):
                        strings[10] = totalMarkEmpl;
                        break;
                    case ("ДЕКАБРЬ"):
                        strings[11] = totalMarkEmpl;
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
