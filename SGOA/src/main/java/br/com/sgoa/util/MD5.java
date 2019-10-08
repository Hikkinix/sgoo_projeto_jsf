package br.com.sgoa.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {
    public static String main(String senha) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        return new BigInteger(1, m.digest()).toString(16);
    }
}
