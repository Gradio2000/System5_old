package com.example.system5.controller.adminController;

import com.example.system5.model.Position;
import com.example.system5.model.Role;
import com.example.system5.model.User;
import com.example.system5.repository.PositionRepository;
import com.example.system5.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/user")
@Slf4j
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

    @PostMapping("/setrole")
    @ResponseBody
    public Map<String, String> setAdminRole(@RequestParam Integer id){
        Map<String, String> result = new HashMap<>();
        Position position = positionRepository.getByPosition_id(id);
        User user = position.user;
        Set<Role> roleSet = user.getRoles();
        if (roleSet.contains(Role.ADMIN)){
            roleSet.clear();
            roleSet.add(Role.USER);
            user.setRoles(roleSet);
            userRepository.save(user);
            result.put("res", "not admin");
        }
        else {
            roleSet.add(Role.ADMIN);
            user.setRoles(roleSet);
            userRepository.save(user);
            result.put("res", "admin");
        }
        return result;
    }
}
