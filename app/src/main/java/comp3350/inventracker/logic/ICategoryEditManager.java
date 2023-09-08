package comp3350.inventracker.logic;

import comp3350.inventracker.exceptions.CategoryAlreadyExistException;
import comp3350.inventracker.exceptions.CategoryNotFoundException;

public interface ICategoryEditManager {
    void AddCategory(String name, int storageSize)
        throws CategoryAlreadyExistException;
}
