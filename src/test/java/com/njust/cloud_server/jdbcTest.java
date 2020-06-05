package com.njust.cloud_server;


import com.njust.cloud_server.dao.IADDao;
import com.njust.cloud_server.dao.IRDDao;
import com.njust.cloud_server.dao.IUserDao;
import com.njust.cloud_server.domain.AverageData;
import com.njust.cloud_server.domain.ResultData;
import com.njust.cloud_server.domain.User;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class jdbcTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    IRDDao iRDDao;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println("dataSource: " + dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println("connection: " + connection);
        connection.close();
    }

    @Test
    public void testMybatis(){
        ResultData resultData = new ResultData();
        resultData.setDataCount(100);
        resultData.setEarliestDate(new Date());
        resultData.setLatestDate(new Date());
        resultData.setSumOfHeartRate(new BigInteger("100"));
        resultData.setSumOfTemperature(new BigInteger("100"));
        resultData.setUsername("Yang");
        iRDDao.add(resultData);
    }



}
