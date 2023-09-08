package comp3350.inventracker.app;

import static comp3350.inventracker.app.AppPersistence.getDBPathName;

import comp3350.inventracker.daos.hsqldb.CategoriesDao;
import comp3350.inventracker.daos.hsqldb.InventoryDao;
import comp3350.inventracker.daos.hsqldb.InventoryLogisticsDao;
import comp3350.inventracker.daos.hsqldb.ProductAvailabilityDao;
import comp3350.inventracker.daos.hsqldb.ProductModelDao;
import comp3350.inventracker.daos.hsqldb.ProductSearchDao;
import comp3350.inventracker.daos.hsqldb.ProductsDao;
import comp3350.inventracker.daos.hsqldb.TrackingDao;
import comp3350.inventracker.daos.hsqldb.UserLoginsDao;
import comp3350.inventracker.daos.hsqldb.WarehouseAnalyticsDao;
import comp3350.inventracker.daos.hsqldb.WarehouseDao;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.daos.IInventoryDao;
import comp3350.inventracker.daos.IInventoryLogisticsDao;
import comp3350.inventracker.daos.IProductAvailabilityDao;
import comp3350.inventracker.daos.IProductModelDao;
import comp3350.inventracker.daos.IProductSearchDao;
import comp3350.inventracker.daos.IProductsDao;
import comp3350.inventracker.daos.ITrackingDao;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.daos.IWarehouseAnalyticsDao;
import comp3350.inventracker.daos.IWarehouseDao;

public class Repositories {
    
    private static IInventoryDao          inventoryDao;
    private static IInventoryLogisticsDao inventoryLogisticsDao;
    private static ITrackingDao           trackingDao;
    private static ICategoriesDao         categoryDao;
    private static IWarehouseDao     warehouseDao;
    private static IProductsDao      productsDao;
    private static IProductSearchDao productSearchDao;
    
    private static IWarehouseAnalyticsDao  warehouseAnalyticsDao;
    private static IProductAvailabilityDao productAvailabilityDao;
    
    public static IInventoryLogisticsDao getInventoryLogisticsDao() {
        return inventoryLogisticsDao != null
            ? inventoryLogisticsDao
            : (inventoryLogisticsDao = new InventoryLogisticsDao(getDBPathName()));
    }
    
    public static ICategoriesDao getCategoriesDao() {
        return categoryDao != null
            ? categoryDao
            : (categoryDao = new CategoriesDao(getDBPathName()));
    }
    
    public static IWarehouseDao getWarehouseDao() {
        return warehouseDao != null
            ? warehouseDao
            : (warehouseDao = new WarehouseDao(getDBPathName()));
    }
    
    public static IProductsDao getProductsDao() {
        return productsDao != null
            ? productsDao
            : (productsDao = new ProductsDao(getDBPathName()));
    }
    
    public static IProductSearchDao getProductSearchDao() {
        return productSearchDao != null
            ? productSearchDao
            : (productSearchDao = new ProductSearchDao(getDBPathName()));
    }
    
    public static IProductAvailabilityDao getProductAvailabilityDao() {
        if (productAvailabilityDao == null) {
            productAvailabilityDao = new ProductAvailabilityDao(getDBPathName());
        }
        
        return productAvailabilityDao;
    }
    
    public static IWarehouseAnalyticsDao getWarehouseAnalyticsDao() {
        return warehouseAnalyticsDao != null
            ? warehouseAnalyticsDao
            : (warehouseAnalyticsDao = new WarehouseAnalyticsDao(getDBPathName()));
    }
    
    private static IUserLoginsDao userLoginsDao;
    public static IUserLoginsDao getUserLoginsDao() {
        return userLoginsDao != null
            ? userLoginsDao
            : (userLoginsDao = new UserLoginsDao(getDBPathName()));
    }
    private static IProductModelDao productModelDao;
    public static IProductModelDao getProductModelDao(){
        if(productModelDao == null) {
            productModelDao = new ProductModelDao(getDBPathName());
        }
        return productModelDao;
    }
}
