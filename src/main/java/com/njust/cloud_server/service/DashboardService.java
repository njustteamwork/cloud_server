package com.njust.cloud_server.service;

import com.njust.cloud_server.dao.IADDao;
import com.njust.cloud_server.dao.IUserDao;
import com.njust.cloud_server.domain.AverageData;
import com.njust.cloud_server.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * dashboard的service层
 */
@Service
public class DashboardService {

    @Autowired
    private IADDao aDDao;

    @Autowired
    private IUserDao userDao;

    public float getAverageTemperatureOfDay(int dayBefore){
        List<AverageData> list = aDDao.queryByDay(dayBefore);
        float at = 0;
        for(AverageData averageData :list){
            at += averageData.getAverageTemperature();
        }
        at /= list.size();
        if(Double.isNaN(at))
            return 0.0f;
        return at;
    }

    public float getAverageHeartRateOfDay(int dayBefore){
        List<AverageData> list = aDDao.queryByDay(dayBefore);
        float ahr = 0;
        for(AverageData averageData :list){
            ahr += averageData.getAverageHeartRate();
        }
        ahr /= list.size();
        if(Double.isNaN(ahr))
            return 0.0f;
        return ahr;
    }

    public float getCurrentTemperature(){
        List<AverageData> list = aDDao.queryByHour(6);
        float ct = 0;
        for(AverageData averageData :list){
            ct += averageData.getAverageTemperature();
        }
        ct /= list.size();
        if(Double.isNaN(ct))
            return 0.0f;
        return ct;
    }

    public float getCurrentHeartRate(){
        List<AverageData> list = aDDao.queryByHour(6);
        float chr = 0;
        for(AverageData averageData :list){
            chr += averageData.getAverageHeartRate();
        }
        chr /= list.size();
        if(Double.isNaN(chr))
            return 0.0f;
        return chr;
    }

    public List<AverageData> queryAbnormalData(){
        return aDDao.queryAbnormalData();
    }

    public boolean userVerify(User user){
        User oUser = userDao.findByName(user.getUserName());

        return oUser.getPassword().equals(user.getPassword());
    }

}
