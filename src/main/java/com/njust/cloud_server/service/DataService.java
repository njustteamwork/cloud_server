package com.njust.cloud_server.service;


import com.njust.cloud_server.dao.IADDao;
import com.njust.cloud_server.dao.IRDDao;
import com.njust.cloud_server.domain.AverageData;
import com.njust.cloud_server.domain.ResultData;
import com.njust.cloud_server.paillier.PaillierDecryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataService {
    @Autowired
    private IRDDao rdDao; //收到的聚合信息对象的DAO
    @Autowired
    private IADDao adDao; //解密后的数据DAO

    public void saveData(ResultData resultData, PaillierDecryptor paillierDecryptor){
        AverageData averageData = new AverageData();
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
    }

}
