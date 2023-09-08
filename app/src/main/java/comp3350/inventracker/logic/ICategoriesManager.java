package comp3350.inventracker.logic;

import java.util.List;

import comp3350.inventracker.dtos.CategoryRowModel;

public interface ICategoriesManager {
    List<CategoryRowModel> GetAllCategories();
    
    CategoryRowModel GetCategoryById(int categoryId);
}
