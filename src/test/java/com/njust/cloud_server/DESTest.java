package com.njust.cloud_server;

import com.njust.cloud_server.des.Transfer.TransferEncryptorImpl;

public class DESTest {
    public static void main(String[] args) throws Exception {
        String data = "hello!";
        TransferEncryptorImpl test1 = new TransferEncryptorImpl();
        byte[] key = test1.generateKey();
        System.out.println("encrypt:"+new String(test1.encrypt(data,key)));
        test1.saveToFile(key);
        System.out.println("decrypt:"+test1.decrypt(test1.encrypt(data,key),test1.readFromFile()));

    }
}
