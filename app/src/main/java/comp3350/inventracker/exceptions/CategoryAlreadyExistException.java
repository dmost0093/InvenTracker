package comp3350.inventracker.exceptions;

public class CategoryAlreadyExistException extends Exception {
    public CategoryAlreadyExistException(String name){
        super("Category Name: " + name+ " already exist");
    }
}
