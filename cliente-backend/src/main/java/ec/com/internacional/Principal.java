package ec.com.internacional;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Principal {
	
	private static final String SECRET_KEY = "mySecretKey12345"; // Clave de 16 caracteres
    private static final String INIT_VECTOR = "1234567890123456"; // IV de 16 bytes

    public static String encrypt1(String plainText) throws Exception {
        // Convertir clave y vector de inicialización a bytes
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));

        // Configurar el Cipher para AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // Cifrar y codificar a Base64
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt1(String encryptedText) throws Exception {
        // Convertir clave y vector de inicialización a bytes
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));

        // Configurar el Cipher para AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // Decodificar de Base64 y descifrar
        byte[] decodedEncryptedText = Base64.getDecoder().decode(encryptedText);
        byte[] decrypted = cipher.doFinal(decodedEncryptedText);

        return new String(decrypted, "UTF-8");
    }

    
    public static String encrypt(String plainText) throws Exception {
        // Generar un IV aleatorio
        SecureRandom secureRandom = new SecureRandom();
        byte[] ivBytes = new byte[16];
        secureRandom.nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        // Convertir clave a bytes
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        // Configurar el Cipher para AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // Cifrar y codificar a Base64
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = Base64.getEncoder().encodeToString(encrypted);
        String ivString = Base64.getEncoder().encodeToString(ivBytes);

        // Retorna el IV junto con el texto cifrado
        return ivString + ":" + encryptedText;
    }

    public static String decrypt(String encryptedText) throws Exception {
        // Extraer el IV y el texto cifrado
        String[] parts = encryptedText.split(":");
        byte[] ivBytes = Base64.getDecoder().decode(parts[0]);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

        // Convertir clave a bytes
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        // Configurar el Cipher para AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // Descifrar
        byte[] decrypted = cipher.doFinal(encryptedBytes);

        return new String(decrypted, "UTF-8");
    }
    
    
    public static void main(String[] args) {
        try {
            String plainText = "Hola Mundo";

            String encryptedText = encrypt(plainText);
            System.out.println("Texto Cifrado: " + encryptedText);

            String decryptedText = decrypt("tKhHoc2yWVCZV1/xWOfV6g==:+V45ZmEhnqLFhNLo/MAYzw==");
            System.out.println("Texto Descifrado: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
