package com.example.qtest.service;

import com.example.qtest.dto.GroupTestDto;
import com.example.qtest.dto.TestDto;
import com.example.qtest.model.GroupTest;
import com.example.qtest.model.Test;
import org.springframework.stereotype.Service;

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
}
