package Lab4.authentication.utils;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordService {
    public static String encryptPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashPassword = new StringBuilder(no.toString(16));
            while (hashPassword.length() < 32) {
                hashPassword.insert(0, "0");
            }
            return hashPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkPassword(String password, String passwordHash){
        return passwordHash.equals(encryptPassword(password));
    }
}
