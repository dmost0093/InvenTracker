package comp3350.inventracker.logic.impl;

import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.IAccountCreationUtility;
import comp3350.inventracker.logic.IPasswordEncryptionManager;

public class AccountCreationUtility
    implements IAccountCreationUtility {
    
    final IPasswordEncryptionManager encryptionManager;
    
    public AccountCreationUtility(IPasswordEncryptionManager encryptionManager) {
        this.encryptionManager = encryptionManager;
    }
    
    @Override
    public UserLoginDto CreateNewUser(String username, String password, Boolean isAdmin) {
        final String salt = encryptionManager.GetRandomSalt();
        final String hash = encryptionManager.HashPassword(password, salt);
        
        return new UserLoginDto(username, hash, salt, isAdmin);
    }
    
    @Override
    public UserLoginDto CreateNewUser(String username, String password) {
        return CreateNewUser(username, password, false);
    }
}
