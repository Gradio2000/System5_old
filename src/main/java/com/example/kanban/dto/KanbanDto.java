package com.example.kanban.dto;

import com.example.kanban.model.Kanban;
import com.example.system5.dto.UserDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class KanbanDto implements Serializable {
    private Integer id;
    private String kanbanName;
    private Boolean started;
    private Boolean continues;
    private Boolean finished;
    private String describe;
    private UserDto userDto;
    private Date taskEndDate;

    public KanbanDto(Integer id, String kanbanName, Boolean started,
                     Boolean continues, Boolean finished,
                     String describe, UserDto userDto, Date taskEndDate) {
        this.id = id;
        this.kanbanName = kanbanName;
        this.started = started;
        this.continues = continues;
        this.finished = finished;
        this.describe = describe;
        this.userDto = userDto;
        this.taskEndDate = taskEndDate;
    }

    public KanbanDto() {
    }

    public static KanbanDto getInstance(Kanban kanban){
        return new KanbanDto(kanban.getId(), kanban.getKanbanName(), kanban.getStarted(),
                kanban.getContinues(), kanban.getFinished(), kanban.getDescribe(),
                UserDto.getInstance(kanban.getUser()), kanban.getTaskEndDate());
    }
}
