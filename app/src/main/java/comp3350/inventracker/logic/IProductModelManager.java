package comp3350.inventracker.logic;

import java.math.BigDecimal;

import comp3350.inventracker.exceptions.CategoryNotFoundException;
import comp3350.inventracker.exceptions.InvalidCostException;
import comp3350.inventracker.exceptions.InvalidMSRPException;
import comp3350.inventracker.exceptions.ProductModelNameExistsException;

public interface IProductModelManager {
    void AddProductModel(String name, int categoryId, BigDecimal cost,  BigDecimal msrp)
        throws CategoryNotFoundException,
               InvalidCostException,
               InvalidMSRPException,
               ProductModelNameExistsException;
}
