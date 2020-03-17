package com.njust.cloud_server.controller;

import com.google.gson.Gson;
import com.njust.cloud_server.paillier.PaillierDecryptor;
import com.njust.cloud_server.paillier.PaillierPrivateKey;
import com.njust.cloud_server.source.ADDao;
import com.njust.cloud_server.source.AverageData;
import com.njust.cloud_server.source.RDDao;
import com.njust.cloud_server.source.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("data")
public class DataController {
    @Autowired
    private RDDao rdDao;
    @Autowired
    private ADDao adDao;

    @PostMapping("/postRData")
    public String postEData(@RequestParam String data){
        System.out.println("收到聚合信息"+data);
        Gson gson = new Gson();
        ResultData resultData = gson.fromJson(data,ResultData.class);
        rdDao.add(resultData);

        String privateKeyString = "{\"n\":7037996759611275900405487329144489085210900622405788623915340046554895678557675360099993502545810105916795350348201798995744651664108236879690390748857833,\"nSquare\":49533398388298819693190911443085500113137594389227717398938303574532356291531019850234314622241175041250992063305927006862844026670633749958420794136365527887009273250901790502746504678689585917463571409706569379921923499464969602901871572009667889989146252127852333968575165007138552703354893437794045455889,\"g\":47,\"lambda\":879749594951409487550685916143061135651362577800723577989417505819361959819687634117238830220149011780391918958071436016289437839877979357110912709188800,\"timeStamp\":1580452220179}";
        PaillierPrivateKey privateKey = gson.fromJson(privateKeyString,PaillierPrivateKey.class);
        PaillierDecryptor paillierDecryptor = new PaillierDecryptor(privateKey);
        AverageData averageData = new AverageData();
        averageData.setUsername(resultData.getUsername());
        averageData.setEarliestDate(resultData.getEarliestDate());
        averageData.setLatestDate(resultData.getLatestDate());
        averageData.setAverageTemperature(paillierDecryptor.decryptIt(resultData.getSumOfTemperature()).floatValue()/10f/resultData.getDataCount());
        averageData.setAverageHeartRate(paillierDecryptor.decryptIt(resultData.getSumOfHeartRate()).floatValue()/resultData.getDataCount());
        if(averageData.getAverageHeartRate()>55 && averageData.getAverageHeartRate()<110 && averageData.getAverageTemperature()>36 && averageData.getAverageTemperature()<37)
            adDao.add(averageData);
        else
            adDao.addToAbnormal(averageData);
        return "got it";
    }

    @PostMapping("/getFogUrl")
    public String getFogUrl() {
        /**
         * 此处应调用雾节点分配算法
         * 并返回雾节点地址
         */
        String urlString = "http://10.0.2.2:8080";
        System.out.println("返回请求雾节点地址");
        return urlString;
    }
}
