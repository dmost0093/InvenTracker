package comp3350.inventracker.logic.impl;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.exceptions.RegistrationException;
import comp3350.inventracker.logic.IAccountCreationUtility;
import comp3350.inventracker.logic.IRegistrationManager;

public class RegistrationManager
    implements IRegistrationManager {
    
    final IAccountCreationUtility newUserUtility;
    final IUserLoginsDao          loginsDao;
    
    public RegistrationManager(IUserLoginsDao loginsDao, IAccountCreationUtility newUserUtility) {
        this.loginsDao      = loginsDao;
        this.newUserUtility = newUserUtility;
    }
    
    @Override
    public void RegisterNewUser(String username, String password, String confirmPassword, boolean isAdmin)
        throws RegistrationException {
        username = username.toUpperCase();
        AccountValidationUtility.ValidateNewUsername(username);
        AccountValidationUtility.ValidateNewPassword(password, confirmPassword);
        ProcessUserRegistration(username, password, isAdmin);
    }
    
    private void ProcessUserRegistration(String username, String password, boolean isAdmin)
        throws RegistrationException {
        try {
            loginsDao.Insert(newUserUtility.CreateNewUser(username, password, isAdmin));
        }
        catch(PersistenceException.IntegrityConstraintViolation e) {
            throw new RegistrationException.Username(
                String.format("The username: '%s' is taken. Please choose a different username.", username));
        }
        catch (PersistenceException e) {
            throw new RegistrationException(e);
        }
    }
}
