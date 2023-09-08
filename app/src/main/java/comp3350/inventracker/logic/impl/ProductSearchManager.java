package comp3350.inventracker.logic.impl;

import java.util.List;

import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.daos.IProductsDao;
import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.dtos.WarehouseRowModel;

public class ProductSearchManager {
    
    final private IProductsDao      productsDao;
    final private IWarehouseDao     warehouseDao;
    final private ICategoriesDao    categoriesDao;
    
    // TODO might need to refactor this into a few different classes.
    public ProductSearchManager(IProductsDao productsDao, IWarehouseDao warehouseDao, ICategoriesDao categoriesDao) {
        this.productsDao   = productsDao;
        this.warehouseDao  = warehouseDao;
        this.categoriesDao = categoriesDao;
    }
    
    public ProductModelRowModel GetProductModel(int productModelId) {
        return productsDao.SelectPK(productModelId);
    }
    
    public List<WarehouseRowModel> GetWarehouseOptions() {
        return warehouseDao.SelectAll()
                           .Append(new WarehouseRowModel(0, "Any", 0))
                           .OrderBy(x -> x.WarehouseID)
                           .ToList();
    }
    
    public List<CategoryRowModel> GetCategoryOptions() {
        return categoriesDao.SelectAll()
                            .Append(new CategoryRowModel(0, "Any", 0))
                            .OrderBy(x -> x.CategoryId)
                            .ToList();
    }
    
    public List<CategoryRowModel> GetSortingOptions() {
        return categoriesDao.SelectAll()
                            .Append(new CategoryRowModel(0, "Any", 0))
                            .OrderBy(x -> x.CategoryId)
                            .ToList();
    }
}
