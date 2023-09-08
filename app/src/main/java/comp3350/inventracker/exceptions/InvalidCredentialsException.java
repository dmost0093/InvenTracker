package comp3350.inventracker.exceptions;

public class InvalidCredentialsException
    extends Exception {
    
    public InvalidCredentialsException() {
        this("Username or Password is incorrect.");
    }
    
    public InvalidCredentialsException(String string) {
        super(string);
    }
}
