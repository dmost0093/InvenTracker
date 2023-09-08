package comp3350.inventracker.logic.impl;

import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.logic.IRestrictionsManager;

public class RestrictionsManager
    implements IRestrictionsManager {
    
    private static UserLoginDto user;
    
    @Override
    public void SetUser(UserLoginDto user) {
        RestrictionsManager.user = user;
    }
    
    @Override
    public String getUsername() {
        return user != null ? user.Username : null;
    }
    
    @Override
    public boolean IsAdmin() {
        return user != null && user.IsAdmin;
    }
}
