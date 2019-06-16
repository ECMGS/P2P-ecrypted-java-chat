package org.mensajes.crypto;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class Cifrado {

	// Establece una clave por defecto
	private static String KEY = "g¡'90¡òpòp´8486-,-.,<2";
	
	private static SecretKeySpec secretKey;
	private static byte[] key;
	
	// Si el usuario quiere poner otra clave, se cambia
	public Cifrado(String key) {
		KEY = key;
	}
	
	// Convierte la clave tipo String al tipo SecretKeySpec
	private static void setKey() {
		
		MessageDigest sha = null;
		
		try {
			key = KEY.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Cifra el mensaje con la clave.
	public static String cifrarMSG(String msg) {
		String ret;
		
		try {
			setKey();
			Cipher cifrado = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cifrado.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cifrado.doFinal(msg.getBytes()));
		} catch (Exception e) {
			System.err.println("Error al cifrar");
		}
		
		return null;
	}
	
	// Descifra el mensaje con la clave
	public static String descifrarMSG(String msg) {
		
		try {
			setKey();
			Cipher cifrado = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cifrado.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cifrado.doFinal(Base64.getDecoder().decode(msg)));
		} catch (Exception e) {
			System.err.println("Error al descifrar");
			JOptionPane.showMessageDialog(null, "La clave de descifrado no es correcta", "Error al descifrar", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}

}
