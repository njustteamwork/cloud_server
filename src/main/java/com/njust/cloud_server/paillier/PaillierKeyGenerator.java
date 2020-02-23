package com.njust.cloud_server.paillier;
import java.math.*;
import java.util.*;

public class PaillierKeyGenerator {
    //lambda = lcm(p-1, q-1);
    private BigInteger p, q, lambda;

    // n = p*q
    public BigInteger n;

    public BigInteger nsquare;
    /**
     * a random integer in Z*_{n^2} where gcd (L(g^lambda mod nSquare), n) = 1.
     */
    private BigInteger g;
    private int bitLength;

    public PaillierKeyGenerator(){  //default bitLengthVal = 512 certainty = 64
        while(true){
            bitLength = 512;
            p = new BigInteger(bitLength / 2, 64, new Random());
            q = new BigInteger(bitLength / 2, 64, new Random());

            //n = p*q;
            n = p.multiply(q);
            //nSquare = n*n;
            nsquare = n.multiply(n);
            g = new BigInteger( String.valueOf( (int) (  Math.random()*100 ) ));
            //lambda=((p-1)*(q-1)) / gcd(p-1,q-1);
            lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))  //(p-1)*(q-1)
                    .divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
            if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
                System.out.println("g is not good. Choose g again.");
            }
            else break;
        }
    }

    /**
     * publicKey includes n,g       privateKey includes lambda
     * @param bitLengthVal
     * the number of bits of modulus.
     * @param certainty
     * I don't know how to say in English
     */

    public PaillierKeyGenerator(int bitLengthVal, int certainty) {  //recommend bitLengthVal = 512 certainty = 64
        while(true){
            bitLength = bitLengthVal;
            p = new BigInteger(bitLength / 2, certainty, new Random());
            q = new BigInteger(bitLength / 2, certainty, new Random());

            //n = p*q;
            n = p.multiply(q);
            //nSquare = n*n;
            nsquare = n.multiply(n);
            g = new BigInteger( String.valueOf( (int) (  Math.random()*100 ) ));
            //lambda=((p-1)*(q-1)) / gcd(p-1,q-1);
            lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))  //(p-1)*(q-1)
                    .divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
            if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
                System.out.println("g is not good. Choose g again.");
            }
            else break;
        }
    }

    public PaillierPublicKey getPaillierPublicKey(){return new PaillierPublicKey(n, g, bitLength);}
    public PaillierPrivateKey getPaillierPrivateKey(){return new PaillierPrivateKey(n ,g, lambda);}

}
