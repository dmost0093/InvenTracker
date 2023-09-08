package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.utils.Query;

public interface ICategoriesDao {
    int Insert(String categoryName, int storageSize)
        throws PersistenceException;
    Query<CategoryRowModel> SelectAll()
        throws PersistenceException;
    CategoryRowModel SelectPK(int categoryId)
        throws PersistenceException;
    int Update(int categoryId, String newName)
        throws PersistenceException;
}
