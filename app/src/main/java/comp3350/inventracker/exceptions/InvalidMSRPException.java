package comp3350.inventracker.exceptions;

public class InvalidMSRPException
    extends RuntimeException {
    public InvalidMSRPException() {
        super("MSRP must be greater than Cost");
    }
}
