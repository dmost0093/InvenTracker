package comp3350.inventracker.logic.impl;

import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.logic.IPasswordEncryptionManager;
import comp3350.inventracker.logic.IRestrictionsManager;
import comp3350.inventracker.logic.IUserLoginManager;

public class UserLoginManager
    implements IUserLoginManager {
    
    private final IUserLoginsDao userLogins;
    
    private final IPasswordEncryptionManager encryptionManager;
    
    private final IRestrictionsManager restrictionsManager;
    
    
    public UserLoginManager(
        final IUserLoginsDao userLogins,
        final IPasswordEncryptionManager encryptionManager,
        final IRestrictionsManager restrictionsManager
    ) {
        this.userLogins = userLogins;
        this.encryptionManager = encryptionManager;
        this.restrictionsManager = restrictionsManager;
    }
    
    @Override
    public void Login(String username, final String password) throws InvalidCredentialsException {
        username = username.toUpperCase();
    
        // search for entry in UserLogins DB Table
        final UserLoginDto user = userLogins.Select(username);
        if (user == null) {
            throw new InvalidCredentialsException(String.format("Username: '%s' not found.", username));
        }
        
        // then checks password matches.
        if (!encryptionManager.VerifyPassword(user, password)) {
            throw new InvalidCredentialsException(String.format("Password: '%s' is incorrect.", password));
        }
    
        restrictionsManager.SetUser(user);
    }
    
    @Override
    public void LogOut() {
        restrictionsManager.SetUser(null);
    }
}
