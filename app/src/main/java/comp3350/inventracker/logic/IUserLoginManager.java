package comp3350.inventracker.logic;

import comp3350.inventracker.exceptions.InvalidCredentialsException;

public interface IUserLoginManager {
    void Login(String username, String password)
        throws InvalidCredentialsException;
    
    void LogOut();
}

