package com.njust.cloud_server.controller;

import com.njust.cloud_server.source.ADDao;
import com.njust.cloud_server.source.AverageData;
import com.njust.cloud_server.source.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;

@Controller
public class WebController {
    @Autowired
    private ADDao adDao;

    @Autowired
    private DashboardService service;

    @RequestMapping("/web")
    public String test(Model model) {
        AverageData ad = new AverageData();
        ad.setAverageHeartRate(90);
        model.addAttribute("ad", ad);
        model.addAttribute("msg", "this is msg");
        return "hello";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        float[] temperatureOfThisWeek = new float[7];
        float[] temperatureOfLastWeek = new float[7];
        float[] heartRateOfThisWeek = new float[7];
        float[] heartRateOfLastWeek = new float[7];
        float currentTemperature = Math.round(service.getCurrentTemperature() * 10) / 10f;
        float currentHeartRate = Math.round(service.getCurrentHeartRate() * 10) / 10f;
        String temperaturePercent,heartRatePercent;
        DecimalFormat df = new DecimalFormat("#.#%");
        float temp;

        for (int dayBefore = 6; dayBefore >= 0; dayBefore--) {
            temperatureOfThisWeek[dayBefore] = Math.round(service.getAverageTemperatureOfDay(dayBefore) * 10) / 10f;
            heartRateOfThisWeek[dayBefore] = Math.round(service.getAverageHeartRateOfDay(dayBefore) * 10) / 10f;
            temperatureOfLastWeek[dayBefore] = Math.round(service.getAverageTemperatureOfDay(dayBefore + 7) * 10) / 10f;
            heartRateOfLastWeek[dayBefore] = Math.round(service.getAverageHeartRateOfDay(dayBefore + 7) * 10) / 10f;
        }

        temp = (service.getCurrentTemperature()-service.getAverageTemperatureOfDay(1))/service.getAverageTemperatureOfDay(1);
        if(temp>0) temperaturePercent = "<i class=\"fas fa-arrow-up\"></i> " + df.format(temp);
        else if(temp<0) temperaturePercent = "<i class=\"fas fa-arrow-down\"></i> " + df.format(temp);
        else temperaturePercent = "<i class=\"fas fa-arrow-right\"></i> " + df.format(temp);
        temp = (service.getCurrentHeartRate()-service.getAverageHeartRateOfDay(1))/service.getAverageHeartRateOfDay(1);
        if(temp>0) heartRatePercent = "<i class=\"fas fa-arrow-up\"></i> " + df.format(temp);
        else if(temp<0) heartRatePercent = "<i class=\"fas fa-arrow-down\"></i> " + df.format(temp);
        else heartRatePercent = "<i class=\"fas fa-arrow-right\"></i> " + df.format(temp);


        model.addAttribute("temperatureOfLastWeek", temperatureOfLastWeek)
                .addAttribute("temperatureOfThisWeek", temperatureOfThisWeek)
                .addAttribute("heartRateOfLastWeek", heartRateOfLastWeek)
                .addAttribute("heartRateOfThisWeek", heartRateOfThisWeek);

        model.addAttribute("currentTemperature", currentTemperature)
                .addAttribute("currentHeartRate", currentHeartRate);

        model.addAttribute("temperaturePercent",temperaturePercent)
                .addAttribute("heartRatePercent",heartRatePercent);

        //[70, 75, 80, 85, 90, 85, 85]
        return "dashboard";
    }
}
