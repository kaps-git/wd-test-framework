package com.korg.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.testng.annotations.Test;

public class EncryptDecryptUtil {
	static String algorithm = "AES";
	private static byte[] keyValue=new byte[] {'m','y','s','e','c','r','e','t','k','e','y','t','o','u','s','e'};

	public static void useCustomKey(String customkey) {
		keyValue = customkey.getBytes();
	}
	private static Key generateKey() throws Exception {
            Key key = new SecretKeySpec(keyValue, algorithm);
            return key;
    }

	/**
	 * Encrypt value 
	 * @param plainText
	 * @return
	 * @throws Exception
	 * @author akapil
	 */
    public static String encrypt(String plainText) throws Exception {
            Key key = generateKey();
            Cipher chiper = Cipher.getInstance(algorithm);
            chiper.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = chiper.doFinal(plainText.getBytes());
            String encryptedValue = DatatypeConverter.printBase64Binary(encVal);
            return encryptedValue;
    }


    /**
     * Decrypt value
     * @param encryptedText
     * @return
     * @throws Exception
     * @author akapil
     */
    public static String decrypt(String encryptedText) throws Exception {
            // generate key 
            Key key = generateKey();
            Cipher chiper = Cipher.getInstance(algorithm);
            chiper.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = DatatypeConverter.parseBase64Binary(encryptedText);
            byte[] decValue = chiper.doFinal(decordedValue);
            String decryptedValue = new String(decValue);
            return decryptedValue;
    }
    
    @Test
    public void tryEncrypDecrypt() throws Exception {
    	String text = "abcd";
    	
    	String encrVal = encrypt(text);
    	System.out.println("encrypted val :" + encrVal);
    	System.out.println("decrypted val :" + decrypt(encrVal));
    	
    	useCustomKey("aaaabbbbccccdddd");
    	encrVal = encrypt(text);
    	System.out.println("encrypted val :" + encrVal);
    	System.out.println("decrypted val :" + decrypt(encrVal));
    	
    }
}
