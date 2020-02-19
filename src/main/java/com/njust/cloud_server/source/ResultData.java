package com.njust.cloud_server.source;

import java.math.BigInteger;
import java.util.Date;

public class ResultData {
    Long id = null;
    private String username;
    private Date earliestDate;
    private Date latestDate;
    private BigInteger sumOfTemperature;
    private BigInteger sumOfHeartRate;
    private int dataCount;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setEarliestDate(Date earliestDate){
        this.earliestDate = earliestDate;
    }

    public Date getEarliestDate(){
        return earliestDate;
    }

    public void setLatestDate(Date latestDate){
        this.latestDate = latestDate;
    }

    public Date getLatestDate(){
        return latestDate;
    }

    public void setSumOfTemperature(BigInteger sumOfTemperature){
        this.sumOfTemperature = sumOfTemperature;
    }

    public BigInteger getSumOfTemperature() {
        return sumOfTemperature;
    }

    public void setSumOfHeartRate(BigInteger sumOfHeartRate) {
        this.sumOfHeartRate = sumOfHeartRate;
    }

    public BigInteger getSumOfHeartRate() {
        return sumOfHeartRate;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getDataCount() {
        return dataCount;
    }
}
