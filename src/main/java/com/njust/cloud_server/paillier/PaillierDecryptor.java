package com.njust.cloud_server.paillier;

import java.math.BigInteger;

public class PaillierDecryptor {
    private  PaillierPrivateKey privateKey;

    public PaillierDecryptor(PaillierPrivateKey privateKey){
        this.privateKey = privateKey;
    }

    public BigInteger decryptIt(BigInteger c){
        BigInteger u1 = c.modPow(privateKey.getLambda(), privateKey.getnSquare());
        BigInteger u2 = privateKey.getG().modPow(privateKey.getLambda(), privateKey.getnSquare());
        return (u1.subtract(BigInteger.ONE).divide(privateKey.getN())).multiply(u2.subtract(BigInteger.ONE).divide(privateKey.getN()).modInverse(privateKey.getN())).mod(privateKey.getN());
    }
}
