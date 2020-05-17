package com.njust.cloud_server.dao;


import com.njust.cloud_server.domain.AverageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 解密后的数据DAO
 */
@Repository
public class ADDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int add(AverageData averageData){
        return jdbcTemplate.update("insert into average_data(username,earliest_date,latest_date,average_temperature,average_heart_rate) values(?, ?, ?, ?, ?)",averageData.getUsername(),averageData.getEarliestDate(),averageData.getLatestDate(),averageData.getAverageTemperature(),averageData.getAverageHeartRate());
    }

    public int addToAbnormal(AverageData averageData){
        return jdbcTemplate.update("insert into abnormal_data(username,earliest_date,latest_date,average_temperature,average_heart_rate) values(?, ?, ?, ?, ?)",averageData.getUsername(),averageData.getEarliestDate(),averageData.getLatestDate(),averageData.getAverageTemperature(),averageData.getAverageHeartRate());

    }

    public List<AverageData> queryByDay(int dayBefore){
        if(dayBefore>-1 && dayBefore<14)
                return jdbcTemplate.query("SELECT * FROM average_data WHERE TO_DAYS(NOW()) - TO_DAYS(earliest_date) = "+dayBefore,new BeanPropertyRowMapper<AverageData>(AverageData.class));
        else
            return null;
    }

    public List<AverageData> queryByHour(int hourBefore){
        if(hourBefore>-1 && hourBefore<24)
            return jdbcTemplate.query("SELECT * FROM average_data WHERE earliest_date > NOW()-INTERVAL ? HOUR",new BeanPropertyRowMapper<AverageData>(AverageData.class),hourBefore);
        else
            return null;
    }

    public List<AverageData> queryEncryptedDataForms() {
        List<AverageData> list = jdbcTemplate.query("select * from abnormal_data order by id desc limit 4",new BeanPropertyRowMapper<AverageData>(AverageData.class));
        return list;
    }
}
