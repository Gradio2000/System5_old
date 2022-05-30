package com.example.system5.service.system5Service;

import com.example.system5.model.System5;
import com.example.system5.model.TotalMark5;
import com.example.system5.repository.System5Repository;
import org.springframework.stereotype.Service;

@Service
public class SaveOrUpdateSystem5Service {

    private final GetTotalMarkService getTotalMarkService;
    private final System5Repository system5Repository;
    private TotalMark5 totalMark5;

    public SaveOrUpdateSystem5Service(GetTotalMarkService getTotalMarkService, System5Repository system5Repository, TotalMark5 totalMark5) {
        this.getTotalMarkService = getTotalMarkService;
        this.system5Repository = system5Repository;
        this.totalMark5 = totalMark5;
    }

    public  void updateSystem5(System5 system51, System5 system5){
        system51.setRes1(system5.getRes1());
        system51.setRes2(system5.getRes2());
        system51.setRes3(system5.getRes3());
        system51.setRes4(system5.getRes4());
        system51.setRes5(system5.getRes5());

        totalMark5 = system51.getTotalMark5();
        totalMark5.setTotalMark(getTotalMarkService.getTotalMark(system51));
        system51.setTotalMark5(totalMark5);

        system5Repository.save(system51);
    }

    public void saveSystem5(System5 system5){
        totalMark5.setTotalMark(getTotalMarkService.getTotalMark(system5));
        system5.setTotalMark5(totalMark5);
        totalMark5.setSystem5(system5);

        system5Repository.save(system5);
    }
}
