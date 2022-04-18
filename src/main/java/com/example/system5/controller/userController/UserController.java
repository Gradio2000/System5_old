package com.example.system5.controller.userController;

import com.example.system5.dto.UserDTO;
import com.example.system5.model.Position;
import com.example.system5.model.User;
import com.example.system5.repository.PositionRepository;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    public UserController(UserRepository userRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @GetMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getById(@AuthenticationPrincipal AuthUser authUser){
        User user = userRepository.getById(authUser.getUser().getUserId());
        UserDTO userDTO = new UserDTO(user.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/my_employers")
    public ResponseEntity<CollectionModel<Position>> getEmployersList(@AuthenticationPrincipal AuthUser authUser){
        User user = userRepository.getById(authUser.getUser().getUserId());
        int position_id = user.getPosition_id();
        Position position = positionRepository.getByPosition_id(position_id);
        List<Position> positionList = position.getEmployersList();
        return new ResponseEntity<>(CollectionModel.of(positionList), HttpStatus.OK);
    }
}
