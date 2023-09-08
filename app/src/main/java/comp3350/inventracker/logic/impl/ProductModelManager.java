package comp3350.inventracker.logic.impl;

import java.math.BigDecimal;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.daos.IProductModelDao;
import comp3350.inventracker.exceptions.CategoryNotFoundException;
import comp3350.inventracker.exceptions.InvalidCostException;
import comp3350.inventracker.exceptions.InvalidMSRPException;
import comp3350.inventracker.exceptions.ProductModelNameExistsException;
import comp3350.inventracker.logic.IProductModelManager;

public class ProductModelManager implements IProductModelManager {
    
    final private IProductModelDao productModelDao;
    final private ICategoriesDao   categoriesDao;
    
    public ProductModelManager(IProductModelDao productModelDao, ICategoriesDao categoriesDao) {
        this.productModelDao = productModelDao;
        this.categoriesDao   = categoriesDao;
    }
    
    @Override
    public void AddProductModel(String name, int categoryId, BigDecimal cost,  BigDecimal msrp)
        throws CategoryNotFoundException,
               InvalidCostException,
               InvalidMSRPException,
               ProductModelNameExistsException {
        
        name = name.toUpperCase();
    
        if (categoriesDao.SelectPK(categoryId) == null) {
            throw new CategoryNotFoundException(categoryId);
        }
        
        if ((cost.compareTo(BigDecimal.ZERO) <= 0)) {
            throw new InvalidCostException();
        }
        
        if (msrp.compareTo(cost) <= 0) {
            throw new InvalidMSRPException();
        }
        
        try {
            productModelDao.Insert(name, cost, categoryId, msrp);
        }
        catch (PersistenceException e) {
            throw new ProductModelNameExistsException(name);
        }
    }
}
