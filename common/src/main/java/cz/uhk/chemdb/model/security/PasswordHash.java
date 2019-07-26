package cz.uhk.chemdb.model.security;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@Stateless
public class PasswordHash extends BasePasswordHash implements Serializable {

    private final String HASH_ALGORITHM = "SHA-256";

    private MessageDigest messageDigest;

    @PostConstruct
    private void init() {
        try {
            messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
        }
    }

    public String getPasswordHash(String password, String salt) {
        if (password == null || salt == null) return "";
        byte[] passwordBytes = password.getBytes();
        byte[] saltBytes = salt.getBytes();

        ByteBuffer byteBuffer = ByteBuffer.allocate(passwordBytes.length + saltBytes.length);
        byteBuffer.put(saltBytes).put(passwordBytes);
        byte[] digest = messageDigest.digest(byteBuffer.array());
        return Base64.getEncoder().encodeToString(digest);
    }
}