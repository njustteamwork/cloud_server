package com.njust.cloud_server.paillier;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.*;

public class PaillierPrivateKey {
    private BigInteger n;
    private BigInteger nSquare;
    private BigInteger g;
    private BigInteger lambda;
    private String userName;
    private Long timeStamp;

    public PaillierPrivateKey(BigInteger n, BigInteger g, BigInteger lambda) {
        this.n = n;
        nSquare = n.multiply(n);
        this.g = g;
        this.lambda = lambda;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getnSquare() {
        return nSquare;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getLambda() {
        return lambda;
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

    public static PaillierPrivateKey paillierJsonToPrivateKey(String paillierPrivateKey) {
        Gson gson = new Gson();
        return gson.fromJson(paillierPrivateKey, PaillierPrivateKey.class);
    }

    public String getJsonStringPrivateKey() {
        Gson gson = new Gson();
        String jsonStringPrivateKey = gson.toJson(this);
        //System.out.println(jsonStringPublicKey);
        return jsonStringPrivateKey;
    }

    public boolean isTimeUp() {
        Long currentTime = System.currentTimeMillis();
        if (currentTime - timeStamp > 86400000)
            return true;
        return false;
    }

    public boolean saveToFile() throws Exception {
        try {
            String jsonPublicKey = this.getJsonStringPrivateKey();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PAILLIER_PRIVATE_KEY_FILE"));
            oos.writeObject(jsonPublicKey);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static PaillierPrivateKey readFromFile() throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PAILLIER_PRIVATE_KEY_FILE"));
            String jsonPrivateKey = ois.readObject().toString();
            Gson gson = new Gson();
            PaillierPrivateKey paillierPrivateKey = gson.fromJson(jsonPrivateKey, PaillierPrivateKey.class);
            return paillierPrivateKey;
        } catch (Exception e) {
            PaillierKeyGenerator pkg = new PaillierKeyGenerator();
            PaillierPrivateKey privateKey = pkg.getPaillierPrivateKey();
            PaillierPublicKey publicKey = pkg.getPaillierPublicKey();
            privateKey.saveToFile();
            publicKey.saveToFile();
            System.out.println("没有密钥文件，将新建一个密钥对");
            return privateKey;
        }
    }
}
