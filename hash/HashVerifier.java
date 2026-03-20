package hash;

import java.io.File;
import java.nio.file.Files;
import java.security.MessageDigest;

public class HashVerifier {

    public static String generateHash(File file) throws Exception {
        byte[] data = Files.readAllBytes(file.toPath());
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashBytes = md.digest(data);

        StringBuilder hex = new StringBuilder();
        for (byte b : hashBytes) {
            hex.append(String.format("%02x", b));
        }

        return hex.toString();
    }
}