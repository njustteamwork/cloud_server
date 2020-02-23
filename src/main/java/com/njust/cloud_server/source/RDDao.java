package com.njust.cloud_server.source;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RDDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(ResultData rd) {
           return jdbcTemplate.update("insert into result_data(username,earliest_date,latest_date,sum_of_temperature,sum_of_heart_rate,data_count) values(?, ?, ?, ?, ?, ?)",rd.getUsername(),rd.getEarliestDate(),rd.getLatestDate(),rd.getSumOfTemperature(),rd.getSumOfHeartRate(),rd.getDataCount());
    }





}
