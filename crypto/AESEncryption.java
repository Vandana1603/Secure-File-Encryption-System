package crypto;

import utils.PasswordKeyDerivation;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class AESEncryption {

    public static void encryptFile(File inputFile, File outputFile, String password) throws Exception {

        byte[] salt = PasswordKeyDerivation.generateSalt();
        SecretKey key = PasswordKeyDerivation.deriveKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] fileBytes = Files.readAllBytes(inputFile.toPath());
        byte[] encryptedBytes = cipher.doFinal(fileBytes);

        FileOutputStream fos = new FileOutputStream(outputFile);

        fos.write(salt);              // first 16 bytes = salt
        fos.write(encryptedBytes);   // rest = encrypted data

        fos.close();
    }

    public static void decryptFile(File inputFile, File outputFile, String password) throws Exception {

        byte[] fileBytes = Files.readAllBytes(inputFile.toPath());

        byte[] salt = new byte[16];
        System.arraycopy(fileBytes, 0, salt, 0, 16);

        byte[] encryptedBytes = new byte[fileBytes.length - 16];
        System.arraycopy(fileBytes, 16, encryptedBytes, 0, encryptedBytes.length);

        SecretKey key = PasswordKeyDerivation.deriveKey(password, salt);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        Files.write(outputFile.toPath(), decryptedBytes);
    }
}