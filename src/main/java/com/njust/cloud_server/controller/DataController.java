package com.njust.cloud_server.controller;

import com.google.gson.Gson;
import com.njust.cloud_server.source.RDDao;
import com.njust.cloud_server.source.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class DataController {
    @Autowired
    private RDDao rdDao;

    @PostMapping("/postRData")
    public String postEData(@RequestParam String data){
        System.out.println(data);
        Gson gson = new Gson();
        ResultData resultData = gson.fromJson(data,ResultData.class);
        rdDao.add(resultData);
        return data;
    }
}
