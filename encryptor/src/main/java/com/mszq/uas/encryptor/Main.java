package com.mszq.uas.encryptor;

import com.mszq.uas.encryptor.util.AESCoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void  main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println(AESCoder.encrypt("123456","SMW+RuTwO5ObncmeF5NjMA=="));
    }
}
