package com.example.kanban.dto;

import com.example.kanban.model.Kanban;
import com.example.qtest.service.DtoUtils;
import com.example.system5.dto.UserDtoNameOnly;
import com.example.system5.dto.UserDtoNameOnlyWithPositionDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class KanbanDto implements Serializable {
    private Integer id;
    private String kanbanName;
    private Boolean started;
    private Boolean continues;
    private Boolean finished;
    private String describe;
    private UserDtoNameOnlyWithPositionDto userDtoNameOnlyWithPositionDto;
    private Date taskEndDate;
    private List<UserDtoNameOnly> userDtoNameOnlyList;
    private Boolean arch;

    public KanbanDto(Integer id, String kanbanName, Boolean started,
                     Boolean continues, Boolean finished,
                     String describe, UserDtoNameOnlyWithPositionDto userDtoNameOnlyWithPositionDto,
                     Date taskEndDate, List<UserDtoNameOnly> userDtoNameOnlyList, Boolean arch) {
        this.id = id;
        this.kanbanName = kanbanName;
        this.started = started;
        this.continues = continues;
        this.finished = finished;
        this.describe = describe;
        this.userDtoNameOnlyWithPositionDto = userDtoNameOnlyWithPositionDto;
        this.taskEndDate = taskEndDate;
        this.userDtoNameOnlyList = userDtoNameOnlyList;
        this.arch = arch;
    }

    public KanbanDto() {
    }

    public static KanbanDto getInstance(Kanban kanban){
        return new KanbanDto(kanban.getId(), kanban.getKanbanName(), kanban.getStarted(),
                kanban.getContinues(), kanban.getFinished(), kanban.getDescribe(),
                UserDtoNameOnlyWithPositionDto.getInstance(kanban.getUser()), kanban.getTaskEndDate(),
                DtoUtils.convertToUserDtoNameOnlyList(kanban.getUserList()), kanban.getArch());
    }
}
