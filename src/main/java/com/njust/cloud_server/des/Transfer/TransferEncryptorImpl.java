package com.njust.cloud_server.des.Transfer;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public  class TransferEncryptorImpl implements ITransferEncryptor {
    // 产生一个新密钥
    public  byte[] generateKey()throws Exception{
        //密钥生成器
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        //初始化密钥生成器
        keyGen.init(168);   //可指定密钥长度为112或168，默认168
        //生成密钥
        SecretKey secretKey = keyGen.generateKey();


        return secretKey.getEncoded();
    }

    @Override
    public void saveToFile(byte[] secretKey) throws IOException {
        //写入文件保存
        File file=new File("./secretKey.txt");
        OutputStream outputStream=new FileOutputStream(file);
        outputStream.write(secretKey);
        outputStream.close();
        System.out.println(secretKey+":secretKey保存成功");
    }

    // 从文件中读取密钥
    public byte[] readFromFile() throws IOException {

            File file = new File("./secretKey.txt");
            InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        byte[] buffer=new byte[inputStream.available()];
        int n=0;
        while((n=inputStream.read(buffer))!=-1)
            bos.write(buffer,0,n);
        bos.close();
        inputStream.close();
        return bos.toByteArray();
    }

    // 加密json字符串
    public byte[] encrypt(String jsonString,byte[] key)throws Exception{
        //恢复密钥
        SecretKey secretKey = new SecretKeySpec(key, "DESede");
        //Cipher完成加密
        Cipher cipher = Cipher.getInstance("DESede");
        //cipher初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypt = cipher.doFinal(jsonString.getBytes());
        return encrypt;
    }

    // 解密json字符串
    public String decrypt(byte[] bytes,byte[] key)throws Exception{
        //恢复密钥
        SecretKey secretKey = new SecretKeySpec(key, "DESede");
        //Cipher完成解密
        Cipher cipher = Cipher.getInstance("DESede");
        //初始化cipher
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String plain = new String(cipher.doFinal(bytes));
        return plain;
    }
}
