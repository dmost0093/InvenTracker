package comp3350.inventracker.logic.impl;

import comp3350.inventracker.exceptions.RegistrationException;

public enum AccountValidationUtility {
    ;
    
    public static void ValidateNewUsername(final String username)
        throws RegistrationException.Username {
        if (username.isEmpty()) {
            throw new RegistrationException.Username("Enter a Username!");
        }
        
        if (username.length() < 4) {
            throw new RegistrationException.Username("Username must be at least 4 characters");
        }
    }
    
    public static void ValidateNewPassword(final String password, final String confirmPassword)
        throws RegistrationException.Password, RegistrationException.PasswordConfirmation {
        if (password.isEmpty()) {
            throw new RegistrationException.Password("Enter a Password!");
        }
        
        if (password.length() < 4) {
            throw new RegistrationException.Password("Password must be at least 4 characters");
        }
        
        if (confirmPassword.isEmpty()) {
            throw new RegistrationException.PasswordConfirmation("Please confirm your password.");
        }
        
        if (password.compareTo(confirmPassword) != 0) {
            throw new RegistrationException.PasswordConfirmation("Passwords must match!");
        }
    }
}
