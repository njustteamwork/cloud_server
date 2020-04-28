package com.njust.cloud_server.controller;

import com.google.gson.Gson;
import com.njust.cloud_server.paillier.PaillierDecryptor;
import com.njust.cloud_server.paillier.PaillierKeyGenerator;
import com.njust.cloud_server.paillier.PaillierPrivateKey;
import com.njust.cloud_server.paillier.PaillierPublicKey;
import com.njust.cloud_server.dao.ADDao;
import com.njust.cloud_server.domain.AverageData;
import com.njust.cloud_server.dao.RDDao;
import com.njust.cloud_server.domain.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("data")
public class DataController {
    public DataController(){
    }

    @Autowired
    private RDDao rdDao;
    @Autowired
    private ADDao adDao;

    //private String privateKeyString = "{\"n\":7037996759611275900405487329144489085210900622405788623915340046554895678557675360099993502545810105916795350348201798995744651664108236879690390748857833,\"nSquare\":49533398388298819693190911443085500113137594389227717398938303574532356291531019850234314622241175041250992063305927006862844026670633749958420794136365527887009273250901790502746504678689585917463571409706569379921923499464969602901871572009667889989146252127852333968575165007138552703354893437794045455889,\"g\":47,\"lambda\":879749594951409487550685916143061135651362577800723577989417505819361959819687634117238830220149011780391918958071436016289437839877979357110912709188800,\"timeStamp\":1580452220179}";
    private Gson gson = new Gson();
    private PaillierPrivateKey privateKey = PaillierPrivateKey.readFromFile();
    private PaillierDecryptor paillierDecryptor = new PaillierDecryptor(privateKey);
    private AverageData averageData = new AverageData();


    @PostMapping("/postRData")
    public String postEData(@RequestParam String data){
        System.out.println("收到聚合信息" + data);
        ResultData resultData = gson.fromJson(data, ResultData.class);
        if(privateKey.getTimeStamp()!= resultData.getKeyTimeStamp()){ //密钥错误
            return "WKTS";
        }
        rdDao.add(resultData);
        averageData.setUsername(resultData.getUsername());
        averageData.setEarliestDate(resultData.getEarliestDate());
        averageData.setLatestDate(resultData.getLatestDate());
        averageData.setAverageTemperature(paillierDecryptor.decryptIt(resultData.getSumOfTemperature()).floatValue() / 10f / resultData.getDataCount());
        averageData.setAverageHeartRate(paillierDecryptor.decryptIt(resultData.getSumOfHeartRate()).floatValue() / resultData.getDataCount());
        if (averageData.getAverageHeartRate() > 55 && averageData.getAverageHeartRate() < 110 && averageData.getAverageTemperature() > 36 && averageData.getAverageTemperature() < 37)
            adDao.add(averageData);
        else
            adDao.addToAbnormal(averageData);
        if(privateKey.isTimeUp()){
            System.out.println("密钥过期，将新建一个密钥对");
            PaillierKeyGenerator pkg = new PaillierKeyGenerator();
            privateKey = pkg.getPaillierPrivateKey();
            PaillierPublicKey publicKey = pkg.getPaillierPublicKey();
            privateKey.saveToFile();
            publicKey.saveToFile();
            return "WKTS";
        }
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

    @PostMapping("/getPublicKey")
    public String getPublicKey(){
        PaillierPublicKey publicKey = PaillierPublicKey.readFromFile();
        System.out.println("返回公钥");
        return publicKey.getJsonStringPublicKey();
    }
}
