package com.njust.cloud_server.dao;

import com.njust.cloud_server.domain.ResultData;
import org.springframework.stereotype.Repository;

@Repository
public interface IRDDao {

    int add(ResultData rd);

}
