package comp3350.inventracker.logic.impl;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.exceptions.CategoryAlreadyExistException;
import comp3350.inventracker.exceptions.CategoryNotFoundException;
import comp3350.inventracker.logic.ICategoryEditManager;

public class CategoryEditManager
    implements ICategoryEditManager {
    final ICategoriesDao categoriesDao;
    
    public CategoryEditManager(ICategoriesDao categoriesDao) {
        this.categoriesDao = categoriesDao;
    }
    
    //Add row to category
    @Override
    public void AddCategory(String categoryName, int storageSize)
        throws CategoryAlreadyExistException {
        
        categoryName = categoryName.toUpperCase();
        
        try {
            categoriesDao.Insert(categoryName, storageSize);
        }
        catch (PersistenceException e) {
            throw new CategoryAlreadyExistException(categoryName);
        }
    }
}
