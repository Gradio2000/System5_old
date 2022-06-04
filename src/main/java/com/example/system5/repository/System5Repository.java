package com.example.system5.repository;

import com.example.system5.model.System5;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface System5Repository extends JpaRepository<System5, Integer> {
    List<System5> findAllByUserId(Integer user_id);
    List<System5> findByUserIdOrderBySystem5Id(int id);
    List<System5> findAllByMonth(String month);
    System5 findByMonthAndUserId(String month, Integer id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM system5 WHERE month IN (N'ЯНВАРЬ', N'ФЕВРАЛЬ', N'МАРТ'," +
                    "N'АПРЕЛЬ', N'МАЙ', N'ИЮНЬ')")
    List<System5> findFirstHalfYear();

    @Query(nativeQuery = true,
            value = "SELECT * FROM system5 WHERE month IN (N'ИЮЛЬ', N'АВГУСТ', N'СЕНТЯБРЬ'," +
                    "N'ОКТЯБРЬ', N'НОЯБРЬ', N'ДЕКАБРЬ')")
    List<System5> findSecondHalfYear();
}
