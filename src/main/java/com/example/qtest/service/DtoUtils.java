package com.example.qtest.service;

import com.example.qtest.dto.AppointTestDto;
import com.example.qtest.dto.AttempttestDto;
import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.dto.TestDto;
import com.example.qtest.model.AppointTest;
import com.example.qtest.model.Attempttest;
import com.example.qtest.model.GroupTest;
import com.example.qtest.model.Test;
import com.example.system5.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoUtils {

    public GroupTestDto convertToGroupTestDto(GroupTest groupTest){
        return new GroupTestDto(groupTest.getGrouptestId(), groupTest.getName());
    }

    public GroupTestDto convertToGroupTestDtoWithTestName(GroupTest groupTest){
        return new GroupTestDto(groupTest.getGrouptestId(), groupTest.getName(),
                groupTest.getTests().stream().map(this::convertToTestDto).collect(Collectors.toList()));
    }

    public TestDto convertToTestDto(Test test){
        return new TestDto(test.getTestId(), test.getTestName(), test.getCriteria(),
                test.getTime(), test.getQuesAmount(),test.getQuesMix(), test.getDeleted());
    }

    public List<AttempttestDto> convertToAttempttestDtoList(List<Attempttest> attempttestList){
        return attempttestList.stream()
                .map(AttempttestDto::getInstance)
                .collect(Collectors.toList());
    }

    public List<AppointTestDto> convertToAppointTestDtoList(List<AppointTest> appointTestList){
        List<AppointTestDto> appointTestDtoList = new ArrayList<>();
        for (AppointTest appointTest: appointTestList){
            AppointTestDto appointTestDto = new AppointTestDto();
            appointTestDto.setTestDto(TestDto.getInstance(appointTest.getTest()));
            appointTestDto.setUserDto(UserDto.getInstance(appointTest.getUser()));
            appointTestDto.setFinished(appointTest.getFinished());
            appointTestDto.setBase(appointTest.getBase());
            if (appointTest.getAttempttest() != null){
                appointTestDto.setAttempttestDto(AttempttestDto.getInstance(appointTest.getAttempttest()));
            }
            appointTestDtoList.add(appointTestDto);
        }
        return appointTestDtoList;
    }
}
