package eRPapp.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncryptor {
	
	// SHA256 Encryption
	//public static String getSHA256 (String data, int key) {
	public static String getSHA256 (String data, int key) {         
		String resultSHA256 = null; 
		int cipherKey = 0;
		//int cipherKey = key;
		
		try {             
			MessageDigest digest = MessageDigest.getInstance("SHA-256");             
			byte[] hash = digest.digest(data.getBytes("UTF-8"));             
			return DatatypeConverter.printHexBinary(hash);         
			} catch(Exception ex) {             
				ex.printStackTrace();         
			}  
		
		return resultSHA256;     
	}
	
	// BCrypt+salt Encryption
	//public static String getJBCrypt (String data, int key) {
	public static String getJBCrypt (String data) {
		String resultJBCrypt = null;
		int cipherKey = 16;
		//int cipherKey = key;
		
		resultJBCrypt = BCrypt.hashpw(data, BCrypt.gensalt(cipherKey));
		return resultJBCrypt;
	}
	
	// MD5 Encryption
	//public static String getMD5 (String data, int key){
	public static String getMD5 (String data){
		String resultMD5 = null;
		int cipherKey = 16;
		//int cipherKey = key;
		
		byte[] secretBytes = null;
		
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
						data.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		resultMD5 = new BigInteger(1, secretBytes).toString(cipherKey);
		for (int i = 0; i < 32 - resultMD5.length(); i++) {
			resultMD5 = "0" + resultMD5;
		}
		return resultMD5;
	}
	
}
