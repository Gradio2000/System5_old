package com.example.system5.controller.userController;

import com.example.system5.controller.System5Controller;
import com.example.system5.dto.UserDTO;
import com.example.system5.model.Position;
import com.example.system5.model.User;
import com.example.system5.repository.PositionRepository;
import com.example.system5.repository.UserRepository;
import com.example.system5.util.AuthUser;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        for (Position position1 : positionList){
            User user1 = position1.getUser();
            if (user1 != null){
                int id = user1.getUserId();
                Link link = linkTo(methodOn(System5Controller.class).getByUserId(id)).withSelfRel();
                user1.add(link);
            }
        }

        return new ResponseEntity<>(CollectionModel.of(positionList), HttpStatus.OK);
    }
}
