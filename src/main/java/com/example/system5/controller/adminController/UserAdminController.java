package com.example.system5.controller.adminController;

import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
    UserRepository userRepository;

    public UserAdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id){
        userRepository.deleteUser(id);
        return "redirect:/admin/shtat";
    }

    @GetMapping("/get")
    @ResponseBody
    public CollectionModel<User> getUsers(){
        return CollectionModel.of(userRepository.findAll());
    }
}
