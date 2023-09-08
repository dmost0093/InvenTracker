package comp3350.inventracker.logic;

import comp3350.inventracker.exceptions.RegistrationException;

public interface IRegistrationManager {
    void RegisterNewUser(String username, String password, String confirmPassword, boolean isAdmin)
        throws RegistrationException;
}
