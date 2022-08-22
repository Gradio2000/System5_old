package com.example.system5.dto;

import com.example.system5.model.Position;
import lombok.Data;

import java.io.Serializable;

@Data
public class PositionDtoNameOnly implements Serializable {
    private int position_id;
    private String position;

    public PositionDtoNameOnly(int position_id, String position) {
        this.position_id = position_id;
        this.position = position;
    }

    public PositionDtoNameOnly() {
    }

    public static PositionDtoNameOnly getInstance(Position position){
        return new PositionDtoNameOnly(position.getPosition_id(), position.getPosition());
    }
}
