package comp3350.inventracker.logic;

import comp3350.inventracker.exceptions.InvalidCredentialsException;
import comp3350.inventracker.exceptions.RegistrationException;

public interface IPasswordUpdateManager {
    void UpdatePassword(String username, String oldPassword, String newPassword, String confirmPassword)
        throws InvalidCredentialsException,
               RegistrationException.Password,
               RegistrationException.PasswordConfirmation;
}
