package com.htf.bigdata.invest.platform.component.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密工具类
 * 
 * @author zhairp createDate: 2019-06-04
 */
public class RsaUtil {

	private static final String KEY_ALGORITHM = "RSA";

	private final static String defaultPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCUrvrsCZwoWBa/ZVUkcIqa/ELxImBkr7f3aLd5aIUzaSJl+eZc7KPKnmr1oVILE3oRt3jrMv2jYAjNCqwDZoQXbNbOKjIKi0f68b8S7wAOxB9Cf2ni6xLaqV0Pz8YglglO73Ja83HMFpM9+sp7GtseMMsFfuOZwr7WKKm00zNzRwIDAQAB";
	private final static String defaultPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJSu+uwJnChYFr9lVSRwipr8QvEiYGSvt/dot3lohTNpImX55lzso8qeavWhUgsTehG3eOsy/aNgCM0KrANmhBds1s4qMgqLR/rxvxLvAA7EH0J/aeLrEtqpXQ/PxiCWCU7vclrzccwWkz36ynsa2x4wywV+45nCvtYoqbTTM3NHAgMBAAECgYAONXanTffNZzWtTsrHM7YbP5ZKIwXhOWW0UB6QjKuGETsHUc1w/fBdMax5EtSbWF4nCUiV+YaBsXDF34o2vPpMeX2aZwz7YZ0GsQmeEVx0rH+VHki7HlNgghUlz/hDw+c1hWnuknV6fHceWC2OAiPadBIZw41JOz5mAwK/zDzIOQJBAMiHqEt/8dmruLcWSTo5r5VBlzZdn386LqYtXMN06WH64Pti/e0pHByLZVNKdJupxYSfCSlJn7TMcHbvBwloA3UCQQC9z94bivYhThfhS0L7q+ycxKtU6GZJCqDAx08ans6jZp7QevJoGH+AUjSm2agWjvd5ljn88QyhhUqeSFGIsrBLAkEAl3gYwEa5iSCz2t/jmi5+mTA9JtybIH/zQqSbICyMKO1Wyawhf+WAjXMhq0NfR0F6p811HxG0p92QydWNLCa8DQJADrdD+AyxAmoEjfIA0nRE1u6kfeO6smUBi5CoQF8ZlC+LloxBqPJlSDSXYmwzjPQumZ2dB1BE82zbGSRwNdHsuwJAYAb3fa3rpvSip5W5SPMJUPLVcONE1x6Q7558raXxtFcqsiAaXram5jp2FXIAnorIpO8SgI9Y/gfankwm6TGMIg==";

	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	/**
	 * 2015-12-26 RSA加密
	 */
	public static String encryptData(String data) {
		return encryptData(data, defaultPublicKey);
	}

	public static String encryptData(String data, String publicKey) {
		try {
			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM, "BC");
			PublicKey pubKey = keyf.generatePublic(pubX509);

			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] dataToEncrypt = data.getBytes("utf-8");
			byte[] encryptedData = cipher.doFinal(dataToEncrypt);
			String encryptString = Base64.encodeBase64String(encryptedData);
			return encryptString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA解密
	 */
	public static String decryptData(String data) {
		return decryptData(data, defaultPrivateKey);
	}

	public static String decryptData(String data, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM, "BC");
			PrivateKey privKey = keyf.generatePrivate(priPKCS8);

			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			byte[] descryptData = Base64.decodeBase64(data);
			byte[] descryptedData = cipher.doFinal(descryptData);
			String srcData = new String(descryptedData, "utf-8");
			return srcData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成key对
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String, String> genKey() throws NoSuchAlgorithmException {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom random = new SecureRandom();
		// 初始加密，512位已被破解，用1024位,最好用2048位
		keygen.initialize(1024, random);
		// 取得密钥对
		KeyPair kp = keygen.generateKeyPair();
		PrivateKey privateKey = kp.getPrivate();
		String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
		PublicKey publicKey = kp.getPublic();
		String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());

		String testString = "testString";
		String encrypt = encryptData(testString, publicKeyString);
		if (decryptData(encrypt, privateKeyString).equals(testString)) {
			System.out.println("PUBLIC_KEY: " + publicKeyString);
			System.out.println("PRIVATE_KEY: " + privateKeyString);
			Map<String, String> keyMap = new HashMap<>();
			keyMap.put("PUBLIC_KEY", publicKeyString);
			keyMap.put("PRIVATE_KEY", privateKeyString);
			return keyMap;
		} else {
			System.out.println("genKey failed");
			return null;
		}
	}
}
