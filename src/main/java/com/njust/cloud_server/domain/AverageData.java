package com.njust.cloud_server.domain;

import java.util.Date;

/**
 * 解密后的数据实体类
 */
public class AverageData {
    private Long id = null;
    private String username;
    private Date earliestDate;
    private Date latestDate;
    private float averageTemperature;
    private float averageHeartRate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getEarliestDate() {
        return earliestDate;
    }

    public void setEarliestDate(Date earliestDate) {
        this.earliestDate = earliestDate;
    }

    public Date getLatestDate() {
        return latestDate;
    }

    public void setLatestDate(Date latestDate) {
        this.latestDate = latestDate;
    }

    public float getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(float averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public float getAverageHeartRate() {
        return averageHeartRate;
    }

    public void setAverageHeartRate(float averageHeartRate) {
        this.averageHeartRate = averageHeartRate;
    }
}
