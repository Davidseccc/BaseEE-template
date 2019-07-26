package cz.uhk.chemdb.bean;

import org.apache.deltaspike.core.util.ExceptionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Named
@Stateless
public class PasswordHasher implements Serializable {

    private MessageDigest messageDigest;

    @PostConstruct
    private void postConstruct() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            ExceptionUtils.throwAsRuntimeException(e);
        }
    }

    /**
     * Password-specific hash function
     *
     * @param password Password to hash
     * @param salt     User-specific salt
     * @return Hashed password & salt variation, represented as a BASE64 String
     */
    public String hashPassword(String password, String salt) {
        byte[] passwordBytes = password.getBytes();
        byte[] saltBytes = salt.getBytes();

        ByteBuffer byteBuffer = ByteBuffer.allocate(passwordBytes.length + saltBytes.length);
        byteBuffer.put(saltBytes);
        byteBuffer.put(passwordBytes);
        byte[] digest = messageDigest.digest(byteBuffer.array());
        return Base64.getEncoder().encodeToString(digest);
    }

}
