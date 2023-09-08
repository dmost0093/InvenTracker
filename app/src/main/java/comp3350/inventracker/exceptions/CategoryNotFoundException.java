package comp3350.inventracker.exceptions;

import java.util.Locale;

public class CategoryNotFoundException
    extends Exception {
    public CategoryNotFoundException(int categoryId) {
        super(String.format(Locale.CANADA, "Category with CategoryId=%d not found.", categoryId));
    }
}

