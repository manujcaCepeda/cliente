package ec.com.internacional.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtils {
	private static final String SECRET_KEY = "mySecretKey12345"; // Clave de 16 caracteres

    public static String decrypt2(String encryptedText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, "UTF-8");
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
    
    
    
    
}
