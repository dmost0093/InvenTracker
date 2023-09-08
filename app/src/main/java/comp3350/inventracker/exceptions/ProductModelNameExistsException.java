package comp3350.inventracker.exceptions;

import java.util.Locale;

public class ProductModelNameExistsException
    extends RuntimeException {
    public ProductModelNameExistsException(String productModelName) {
        super(String.format(Locale.CANADA, "Product Model Name '%s' is not unique. ", productModelName));
    }
}
