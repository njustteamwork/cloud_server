package com.njust.cloud_server.controller;

import com.njust.cloud_server.source.ADDao;
import com.njust.cloud_server.source.AverageData;
import com.njust.cloud_server.source.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private ADDao adDao;

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/test")
    public String test(){
        System.out.println("test");
        System.out.println(dashboardService.getAverageHeartRateOfDay(1));
        System.out.println(adDao.queryByHour(23).get(0).getAverageHeartRate());
        int count = 0;
        for(AverageData averageData : adDao.queryByHour(23)){
            averageData.setAverageHeartRate(120);
            averageData.setAverageTemperature(39);
            adDao.addToAbnormal(averageData);
            count++;
            if(count == 5) break;
        }
        return "hi";
    }
}
