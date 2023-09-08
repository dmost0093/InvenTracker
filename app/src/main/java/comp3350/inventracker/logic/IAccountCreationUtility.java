package comp3350.inventracker.logic;

import comp3350.inventracker.dtos.UserLoginDto;

public interface IAccountCreationUtility {
    UserLoginDto CreateNewUser(String username, String password);
    UserLoginDto CreateNewUser(String username, String password, Boolean isAdmin);
}
