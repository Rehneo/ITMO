package Lab4.authentication.utils;

import java.security.SecureRandom;

public class TokenService {
    public static String generateToken(int length, String username) {
        return new SecureRandom(username.getBytes())
                .ints('0', 'z')
                .filter(s -> s >= '0' && s <= '9' || s >= 'A' && s <= 'Z' || s >= 'a' && s <= 'z')
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
