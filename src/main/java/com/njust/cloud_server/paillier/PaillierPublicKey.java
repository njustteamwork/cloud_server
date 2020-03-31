package com.njust.cloud_server.paillier;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.*;

public class PaillierPublicKey {
    private BigInteger n;
    private BigInteger nSquare;
    private BigInteger g;
    private int bitLength;
    private String userName;
    private Long timeStamp;

    public PaillierPublicKey(BigInteger n, BigInteger g, int bitLength) {
        this.n = n;
        nSquare = n.multiply(n);
        this.g = g;
        this.bitLength = bitLength;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getNSquare() {
        return nSquare;
    }

    public BigInteger getG() {
        return g;
    }

    public int getBitLength() {
        return bitLength;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static PaillierPublicKey paillierJsonToPublicKey(String paillierPublicKey) {
        Gson gson = new Gson();
        return gson.fromJson(paillierPublicKey, PaillierPublicKey.class);
    }

    public String getJsonStringPublicKey() {
        Gson gson = new Gson();
        String jsonStringPublicKey = gson.toJson(this);
        //System.out.println(jsonStringPublicKey);
        return jsonStringPublicKey;
    }

    public boolean isTimeUp() {
        Long currentTime = System.currentTimeMillis();
        if (currentTime - timeStamp > 86400000)
            return true;
        return false;
    }

    /**
     * 暂时以文件方式储存公钥
     * 将在以后改为数据库储存
     */
    public boolean saveToFile() throws Exception {
        try {
            String jsonPublicKey = this.getJsonStringPublicKey();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PAILLIER_PUBLIC_KEY_FILE"));
            oos.writeObject(jsonPublicKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static PaillierPublicKey readFromFile() throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PAILLIER_PUBLIC_KEY_FILE"));
            String jsonPublicKey = ois.readObject().toString();
            Gson gson = new Gson();
            PaillierPublicKey paillierPublicKey = gson.fromJson(jsonPublicKey, PaillierPublicKey.class);
            return paillierPublicKey;
        } catch (Exception e) {
            PaillierKeyGenerator pkg = new PaillierKeyGenerator();
            PaillierPrivateKey privateKey = pkg.getPaillierPrivateKey();
            PaillierPublicKey publicKey = pkg.getPaillierPublicKey();
            privateKey.saveToFile();
            publicKey.saveToFile();
            System.out.println("没有密钥文件，将新建一个密钥对");
            return publicKey;
        }
    }

}
