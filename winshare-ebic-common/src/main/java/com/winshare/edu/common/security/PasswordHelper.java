package com.winshare.edu.common.security;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;


public class PasswordHelper {

    private String algorithmName = "md5";

    private int hashIterations = 1;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

	public String encryptPassword(String password,String salt) {
        String newPassword = new SimpleHash(algorithmName,password,ByteSource.Util.bytes(salt),hashIterations).toHex();
		return newPassword;
    }
	
	public static void main(String[] args) {
		PasswordHelper p = new PasswordHelper();
		System.out.println(p.encryptPassword("123456", "test"));
	}
}