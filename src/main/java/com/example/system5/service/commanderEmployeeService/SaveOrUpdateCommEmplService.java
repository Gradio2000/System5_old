package com.example.system5.service.commanderEmployeeService;

import com.example.system5.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveOrUpdateCommEmplService {
    private final UserRepository userRepository;

    public SaveOrUpdateCommEmplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveOrUpdateCommEmpl(int emplId, int commId){

    }
}
