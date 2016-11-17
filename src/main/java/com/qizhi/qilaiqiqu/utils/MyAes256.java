package com.qizhi.qilaiqiqu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 算法 对称加密，密码学中的高级加密标准 2005年成为有效标准
 * 
 * @author stone
 * @date 2014-03-10 06:49:19
 */
public class MyAes256 {
	static Cipher cipher;
	static final String KEY_ALGORITHM = "AES";
	static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	static final String key = "weride";
	/* 
     *  
     */
	static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	/*
	 * AES/CBC/NoPadding 要求 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16
	 * bytes
	 * 
	 * 由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 * 
	 * 可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 * 其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 * 除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 */
	static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";

	static SecretKey secretKey;
	
	static byte[] getIV() {
		final byte[] iv = new byte[16];
		Arrays.fill(iv, (byte) 0x00);
		return iv;
	}
	
	static {
		if(secretKey == null){
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance("MD5");
				byte[] hash = digest.digest(key.getBytes("UTF-8"));
				secretKey = new SecretKeySpec(hash, "AES");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(cipher == null){
			try {
				cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	//加密
	public static String  encrypt(String str) throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(getIV()));// 使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes()); // 按单部分操作加密或解密数据，或者结束一个多部分操作。
		return Base64.encode(encrypt);
	}
	//解密
	public static String decrypt(String str) throws Exception  {
		byte[] aa = Base64.decode(str);
		cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(getIV()));// 使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(aa);
		return new String(decrypt);
	}
	
}
