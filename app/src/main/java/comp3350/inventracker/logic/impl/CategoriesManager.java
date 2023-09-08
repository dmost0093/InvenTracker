package comp3350.inventracker.logic.impl;

import java.util.List;

import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.logic.ICategoriesManager;

public class CategoriesManager
    implements ICategoriesManager {
    
    final ICategoriesDao categories;
    
    public CategoriesManager(ICategoriesDao categories) {
        this.categories = categories;
    }
    
    @Override
    public List<CategoryRowModel> GetAllCategories() {
        return categories.SelectAll().ToList();
    }
    
    @Override
    public CategoryRowModel GetCategoryById(int categoryId) {
        return categories.SelectPK(categoryId);
    }
}
