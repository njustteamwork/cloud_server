package com.njust.cloud_server.dao;


import com.njust.cloud_server.domain.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 接收到的雾节点数据DAO
 */
@Repository
public class RDDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(ResultData rd) {
           return jdbcTemplate.update("insert into result_data(username,earliest_date,latest_date,sum_of_temperature,sum_of_heart_rate,data_count) values(?, ?, ?, ?, ?, ?)",rd.getUsername(),rd.getEarliestDate(),rd.getLatestDate(),rd.getSumOfTemperature(),rd.getSumOfHeartRate(),rd.getDataCount());
    }





}
