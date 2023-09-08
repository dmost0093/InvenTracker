package comp3350.inventracker.logic;

import comp3350.inventracker.dtos.UserLoginDto;

public interface IPasswordEncryptionManager {
    /**
     * Hash the entered password with the stored salt and compare with the stored hash
     */
    boolean VerifyPassword(UserLoginDto userLogin, String password);
    
    /**
     * @return the result of encrypting password with passwordSalt
     */
    String HashPassword(String password, String passwordSalt);
    
    String GetRandomSalt();
}
