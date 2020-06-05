package com.njust.cloud_server.dao;

import com.njust.cloud_server.domain.AverageData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IADDao {

    void add(AverageData averageData);

    void addToAbnormal(AverageData averageData);

    List<AverageData> queryByDay(int dayBefore);

    List<AverageData> queryByHour(int hourBefore);

    List<AverageData> queryAbnormalData();

}
