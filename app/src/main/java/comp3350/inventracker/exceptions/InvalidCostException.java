package comp3350.inventracker.exceptions;

public class InvalidCostException
    extends RuntimeException {
    public InvalidCostException() {
        super("Cost must be at least 0");
    }
}

