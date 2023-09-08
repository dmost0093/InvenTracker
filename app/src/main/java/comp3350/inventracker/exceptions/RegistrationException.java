package comp3350.inventracker.exceptions;

public class RegistrationException
    extends Exception {
    public RegistrationException(String string) {
        super(string);
    }
    
    public RegistrationException(Throwable cause) {
        super(cause);
    }
    
    public static class Username
        extends RegistrationException {
        public Username(String string) {
            super(string);
        }
    }
    
    public static class Password
        extends RegistrationException {
        public Password(String string) {
            super(string);
        }
    }
    
    public static class PasswordConfirmation
        extends RegistrationException {
        public PasswordConfirmation(String string) {
            super(string);
        }
    }
}
