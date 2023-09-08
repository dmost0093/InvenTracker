package comp3350.inventracker.logic.impl;

import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.IAccountCreationUtility;
import comp3350.inventracker.logic.IPasswordUpdateManager;
import comp3350.inventracker.logic.IUserLoginManager;

public class PasswordUpdateManager
    implements IPasswordUpdateManager {
    
    final IUserLoginsDao          loginsDao;
    final IUserLoginManager       loginManager;
    final IAccountCreationUtility accountCreationUtility;
    
    public PasswordUpdateManager(
        IUserLoginsDao loginsDao,
        IUserLoginManager loginManager,
        IAccountCreationUtility accountCreationUtility
    ) {
        this.loginsDao              = loginsDao;
        this.loginManager           = loginManager;
        this.accountCreationUtility = accountCreationUtility;
    }
    
    @Override
    public void UpdatePassword(String username, String oldPassword, String newPassword, String confirmPassword)
        throws InvalidCredentialsException,
               RegistrationException.Password,
               RegistrationException.PasswordConfirmation {
        
        loginManager.Login(username, oldPassword);
    
        AccountValidationUtility.ValidateNewPassword(newPassword, confirmPassword);
    
        UserLoginDto user = accountCreationUtility.CreateNewUser(username, newPassword);
        
        loginsDao.UpdatePassword(user);
    }
}
