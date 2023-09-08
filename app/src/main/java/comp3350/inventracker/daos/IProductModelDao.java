package comp3350.inventracker.daos;

import java.math.BigDecimal;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.utils.Query;
public interface IProductModelDao {
    int Insert(String name, BigDecimal Cost, int categoryId, BigDecimal MSRP)
        throws PersistenceException;
    Query<ProductModelRowModel> SelectAll()
        throws PersistenceException;
    ProductModelRowModel SelectPk(int productId)
        throws PersistenceException;
    
}
