package comp3350.inventracker.logic;

import comp3350.inventracker.dtos.UserLoginDto;

public interface IRestrictionsManager {
    void SetUser(UserLoginDto user);
    
    String getUsername();
    boolean IsAdmin();
}
