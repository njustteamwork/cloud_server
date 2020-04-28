package com.njust.cloud_server.controller;

import com.njust.cloud_server.dao.ADDao;
import com.njust.cloud_server.dao.UserDao;
import com.njust.cloud_server.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    @Autowired
    private ADDao adDao;

    @Autowired
    private DashboardService service;

    @Autowired
    private UserDao userDao;

    @RequestMapping("/web")
    public String test(Model model) {
        AverageData ad = new AverageData();
        ad.setAverageHeartRate(90);
        model.addAttribute("ad", ad);
        model.addAttribute("msg", "this is msg");
        return "hello";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map =new HashMap<String,Object>();
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        if(!userName.equals("") && password!=""){
            User user =new User(userName,password);
            if(userDao.verify(user)){
                request.getSession().setAttribute("user",user);
                map.put("result","1");
            }
            else map.put("result","-1");
        }else{
            map.put("result","0");
        }
        return map;
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
        List<AverageData> list = adDao.queryEncryptedDataForms();

        for (int dayBefore = 6; dayBefore >= 0; dayBefore--) {
            temperatureOfThisWeek[6-dayBefore] = Math.round(service.getAverageTemperatureOfDay(dayBefore) * 10) / 10f;
            heartRateOfThisWeek[6-dayBefore] = Math.round(service.getAverageHeartRateOfDay(dayBefore) * 10) / 10f;
            temperatureOfLastWeek[6-dayBefore] = Math.round(service.getAverageTemperatureOfDay(dayBefore + 7) * 10) / 10f;
            heartRateOfLastWeek[6-dayBefore] = Math.round(service.getAverageHeartRateOfDay(dayBefore + 7) * 10) / 10f;
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

        model.addAttribute("abnormalData",list);

        return "dashboard";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
}
