package comp3350.inventracker.dtos;

public class UserLoginDto {
    public final String Username;
    public final String PasswordHash;
    public final String  PasswordSalt;
    public final Boolean IsAdmin;
    
    public UserLoginDto(String username, String passwordHash, String passwordSalt) {
        this(username, passwordHash, passwordSalt, false);
    }
    
    public UserLoginDto(String username, String passwordHash, String passwordSalt, Boolean isAdmin) {
        Username     = username;
        PasswordHash = passwordHash;
        PasswordSalt = passwordSalt;
        IsAdmin      = isAdmin;
    }
}

