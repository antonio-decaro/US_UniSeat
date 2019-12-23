package control.utili;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Questa classe definisce l'algoritmo tramite il quale la password viene criptata.
 * */
public class PassowrdEncrypter {

    /**
     * Questo metodo cripta una password
     *
     * @param password password da criptare
     * @return password criptata in SHA-256
     * */
    public static String criptaPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e){
            return password;
        }
    }
}
