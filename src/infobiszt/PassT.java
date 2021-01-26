package infobiszt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import javax.crypto.NoSuchPaddingException;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PassT {

	public static ByteArrayOutputStream bytearray = null;

	public static String Encrypt(byte[] input) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		byte[] keyBytes = getKeyBytes();
		byte[] ivBytes = getIvBytes();
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		ByteArrayInputStream bIn = new ByteArrayInputStream(input);
		bytearray = OutputStream(bIn, cipher);
		String BasicBase64format = Base64.getEncoder().encodeToString(bytearray.toByteArray());

		return BasicBase64format;
	}

	private static byte[] getIvBytes() {
		try {
			String line = LoginPage.Reader();
			String IvString = line.substring(48, 64);
			return IvString.getBytes();

		} catch (IOException e) {
			System.out.println(e + " no hash for key");
			e.printStackTrace();
		}
		System.out.println("null key");
		return null;

	}

	private static byte[] getKeyBytes() {
		try {
			String line = LoginPage.Reader();
			String keyString = line.substring(0, 16);
			return keyString.getBytes();

		} catch (IOException e) {
			System.out.println(e + " no hash for iv");
			e.printStackTrace();
		}
		System.out.println("null iv");
		return null;
	}

	public static ByteArrayOutputStream OutputStream(ByteArrayInputStream bIn, Cipher cipher) throws IOException {
		@SuppressWarnings("resource")
		CipherInputStream cIn = new CipherInputStream(bIn, cipher);
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		int ch;
		while ((ch = cIn.read()) >= 0) {
			bOut.write(ch);
		}
		return bOut;
	}

	public static String Decrypt(String cipherTextString) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		byte[] keyBytes = getKeyBytes();
		byte[] ivBytes = getIvBytes();
		byte[] cipherTextbyte = Base64.getDecoder().decode(cipherTextString);
		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
		bytearray = new ByteArrayOutputStream();
		CipherOutputStream cOut = new CipherOutputStream(bytearray, cipher);
		cOut.write(cipherTextbyte);
		cOut.close();

		String finalString = new String(bytearray.toByteArray());

		return finalString;

	}

	public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public static String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}

}
