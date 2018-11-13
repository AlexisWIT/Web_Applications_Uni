package eRPapp.controller;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class PasswordEncryptor {
	
	public static String getSHA256(String data) {         
		String result = null;         
		try {             
			MessageDigest digest = MessageDigest.getInstance("SHA-256");             
			byte[] hash = digest.digest(data.getBytes("UTF-8"));             
			return DatatypeConverter.printHexBinary(hash);         
			}catch(Exception ex) {             
				ex.printStackTrace();         
				}         
		return result;     
		} 
	
}
