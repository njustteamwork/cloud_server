package com.njust.cloud_server.paillier;
import com.google.gson.Gson;

import java.math.*;

public class PaillierPrivateKey {
    private BigInteger n;
    private BigInteger nSquare;
    private BigInteger g;
    private BigInteger lambda;
    private String userName;
    private Long timeStamp = System.currentTimeMillis();

    public PaillierPrivateKey(BigInteger n, BigInteger g, BigInteger lambda){
        this.n = n;
        nSquare = n.multiply(n);
        this.g = g;
        this.lambda = lambda;
    }

    public BigInteger getN(){return n;}
    public BigInteger getnSquare(){return nSquare;}
    public BigInteger getG(){return g;}
    public BigInteger getLambda(){return lambda;}
    public Long getTimeStamp(){ return timeStamp; }
    public String getUserName(){return userName;}

    public void setUserName(String userName){
        this.userName = userName;
    }

    public static PaillierPrivateKey paillierJsonToPrivateKey(String paillierPrivateKey){
        Gson gson = new Gson();
        return gson.fromJson(paillierPrivateKey,PaillierPrivateKey.class);
    }

    public String getJsonStringPrivateKey(){
        Gson gson = new Gson();
        String jsonStringPrivateKey = gson.toJson(this);
        //System.out.println(jsonStringPublicKey);
        return jsonStringPrivateKey;
    }

}
