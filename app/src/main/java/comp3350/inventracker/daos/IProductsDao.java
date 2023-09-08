package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.ProductModelRowModel;

public interface IProductsDao {
    ProductModelRowModel SelectPK(int productModelId)
        throws PersistenceException;
}
