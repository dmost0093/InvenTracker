package comp3350.inventracker.logic.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.IPasswordEncryptionManager;

public class PasswordEncryptionManager
    implements IPasswordEncryptionManager {
    
    private final MessageDigest messageDigest;
    
    public PasswordEncryptionManager(final MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }
    
    /**
     * Hash the entered password with the stored salt and compare with the stored hash
     */
    @Override
    public boolean VerifyPassword(final UserLoginDto userLogin, final String password) {
        final String hashedPassword = HashPassword(password, userLogin.PasswordSalt);
        return userLogin.PasswordHash.equals(hashedPassword);
    }
    
    /**
     * @return the result of encrypting password with passwordSalt
     */
    @Override
    public String HashPassword(final String password, final String passwordSalt) {
        final String saltedPassword = password + passwordSalt;
        
        // Compute the hash value by passing the salted password as bytes to the digest
        final byte[] hashedBytes = messageDigest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
        
        // Convert the hashed bytes to a hexadecimal string representation
        final StringBuilder hexString = new StringBuilder();
        for (final byte b : hashedBytes) {
            final String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
    
    /**
     * Generate a random salt (encryption key)
     */
    @Override
    public String GetRandomSalt() {
        final SecureRandom random    = new SecureRandom();
        final byte[]       saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
