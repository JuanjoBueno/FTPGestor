package logica.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class AESSimpleManager {

    public static Key obtenerClave (String password, int longitud) {
        Key clave = new SecretKeySpec(password.getBytes (), 0, longitud, "AES");
        return clave;
    }

    public static String cifrar (String textoEnClaro, Key key) throws Exception {
        Cipher cipher = Cipher. getInstance ("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] cipherText = cipher.doFinal(textoEnClaro.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String descifrar (String textoCifrado, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") ;
        cipher.init(Cipher.DECRYPT_MODE, key) ;
        byte [] plainText = cipher.doFinal(Base64.getDecoder().decode(textoCifrado));
        return new String (plainText);
    }
}
