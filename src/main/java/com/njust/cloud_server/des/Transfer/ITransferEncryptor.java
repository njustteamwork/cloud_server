package com.njust.cloud_server.des.Transfer;

import java.io.IOException;

public interface ITransferEncryptor {
    // 产生一个新密钥并将秘钥以文件方式保存
    byte[] generateKey() throws Exception;

    // jiang将秘钥以文件方式保存
    void saveToFile(byte[] secretKey) throws IOException;

    // 从文件中读取密钥
    byte[] readFromFile() throws IOException;

    // 加密json字符串
    byte[] encrypt(String jsonString, byte[] key) throws Exception;

    // 解密json字符串
    String decrypt(byte[] bytes, byte[] key) throws Exception;
}
