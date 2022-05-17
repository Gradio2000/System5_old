package com.example.system5.controller.adminController;

import com.example.system5.model.User;
import com.example.system5.repository.PositionRepository;
import com.example.system5.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
    UserRepository userRepository;
    PositionRepository positionRepository;

    public UserAdminController(UserRepository userRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id){
        userRepository.deleteUser(id);
        return "redirect:/admin/shtat";
    }

    @GetMapping("/get")
    @ResponseBody
    public CollectionModel<User> getUsers(){
        List<User> userList = userRepository.findAll().stream()
                .filter(user -> user.getPosition() == null)
                .collect(Collectors.toList());
        return CollectionModel.of(userList);
    }

    @PostMapping("/insert")
    public String insertUser(@RequestParam int positionId, @RequestParam int userId){
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        user.setPosition(positionRepository.getById(positionId));
        userRepository.save(user);
        return "redirect:/admin/shtat";
    }
}
