package com.example.system5.service.commanderEmployeeService;

import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveOrUpdateCommEmplService {
    private final UserRepository userRepository;

    public SaveOrUpdateCommEmplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveOrUpdateCommEmpl(int comm_id, User user){
        int positoin_id = user.getPosition().getPosition_id();
        if (userRepository.existsCommanderPosition(positoin_id)){
            userRepository.updateCommanderPosition(comm_id, positoin_id);
        }
        else {
            userRepository.commEmpAdd(comm_id, positoin_id);
        }
    }
}
