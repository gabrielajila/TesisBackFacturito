package com.facturito.util;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:seguridad.properties")
public class Seguridad {
	
	@Value( "${PASSPHRASE}" )
	private  String passphrase;
	@Value( "${PSWDITERATIONS}" )
	private  int pswdIterations;
	@Value( "${SALT}" )
	private  String salt;
	@Value( "${ALGORITHMKEY}" )
	private  String ALGORITHMKEY;
	@Value( "${ALGORITHM}" )
	private  String ALGORITHM;
	@Value( "${MODE}" )
	private  String MODE;
	@Value( "${PADDING}" )
	private  String PADDING;
	
	
	@Value( "${ENCODING}" )
	private  Charset PLAIN_TEXT_ENCODING;
	@Value( "${KEY_SIZE_BITS}" )
	private  int KEY_SIZE_BITS;
	private  String CIPHER_TRANSFORMATION;
	
	
	
	public Seguridad() {

	}



	public String decrypt(String encryptedText) throws Exception {
		if (encryptedText != null && !encryptedText.isEmpty()) {
			CIPHER_TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;
			byte[] saltBytes = new String(salt).getBytes(PLAIN_TEXT_ENCODING);
			//SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHMKEY);
			SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHMKEY);
			PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), saltBytes, pswdIterations, KEY_SIZE_BITS);
			SecretKey secretKey = factory.generateSecret(spec);
			SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(new String(salt).getBytes(PLAIN_TEXT_ENCODING));
			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
			byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText);
			byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
			return new String(decryptedTextBytes,PLAIN_TEXT_ENCODING);
		}
		return new String();
	}
	 public String encrypt(String dataText) throws Exception{
	        if (dataText != null && !dataText.isEmpty()) {
	        	CIPHER_TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;
	            //logger.info("Dato a ecriptar---" + dataText);
	        	byte[] saltBytes = new String(salt).getBytes(PLAIN_TEXT_ENCODING);
				SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHMKEY);
				PBEKeySpec spec = new PBEKeySpec(passphrase.toCharArray(), saltBytes, pswdIterations, KEY_SIZE_BITS);
				SecretKey secretKey = factory.generateSecret(spec);
				SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
				IvParameterSpec ivParameterSpec = new IvParameterSpec(new String(salt).getBytes(PLAIN_TEXT_ENCODING));
				Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
				cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
				byte[] encryptedTextBytes = cipher.doFinal(dataText.getBytes(PLAIN_TEXT_ENCODING));
		        return Base64.encodeBase64String(encryptedTextBytes);
	        }
	        return new String();
	    }

}
