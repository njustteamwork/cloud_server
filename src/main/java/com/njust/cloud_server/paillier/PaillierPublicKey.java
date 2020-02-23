package com.njust.cloud_server.paillier;
import com.google.gson.Gson;

import java.math.*;

public class PaillierPublicKey {
    private BigInteger n;
    private BigInteger nSquare;
    private BigInteger g;
    private int bitLength;
    private String userName;
    private Long timeStamp = System.currentTimeMillis();

    public PaillierPublicKey(BigInteger n, BigInteger g, int bitLength){
        this.n = n;
        nSquare = n.multiply(n);
        this.g = g;
        this.bitLength = bitLength;
    }

    public BigInteger getN() {return n;}
    public BigInteger getNSquare(){return nSquare;}
    public BigInteger getG(){return g;}
    public int getBitLength(){return bitLength;}
    public Long getTimeStamp(){ return timeStamp; }
    public String getUserName(){return userName;}

    public void setUserName(String userName){
        this.userName = userName;
    }

    public static PaillierPublicKey paillierJsonToPublicKey(String paillierPublicKey){
        Gson gson = new Gson();
        return gson.fromJson(paillierPublicKey,PaillierPublicKey.class);
    }

    public String getJsonStringPublicKey(){
        Gson gson = new Gson();
        String jsonStringPublicKey = gson.toJson(this);
        //System.out.println(jsonStringPublicKey);
        return jsonStringPublicKey;
    }

}
