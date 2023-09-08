package comp3350.inventracker.daos.hsqldb;

import static comp3350.inventracker.daos.hsqldb.DateUtil.toLocalDate;

import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.EntityMapping;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.InventoryRowModel;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.dtos.TrackingRowModel;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.dtos.WarehouseRowModel;

class DBSchema {
    
    protected static class MapSQLTo {
        public static final EntityMapping<CompleteItemDto> CompleteItem =
            results -> new CompleteItemDto().Set(DBSchema.MapSQLTo.Inventory.mapToEntity(results))
                                            .Set(DBSchema.MapSQLTo.ProductModels.mapToEntity(results))
                                            .Set(DBSchema.MapSQLTo.Categories.mapToEntity(results))
                                            .Set(DBSchema.MapSQLTo.Tracking.mapToEntity(results))
                                            .Set(DBSchema.MapSQLTo.Warehouses.mapToEntity(results));
        
        public static final EntityMapping<InventoryRowModel> Inventory =
            resultSet -> new InventoryRowModel(
                resultSet.getInt(Schema.INVENTORY_ID),
                resultSet.getInt(Schema.PRODUCT_MODEL_ID)
            );
        
        public static final EntityMapping<TrackingRowModel> Tracking =
            resultSet -> {
                try {
                    return new TrackingRowModel(
                        resultSet.getInt(Schema.INVENTORY_ID),
                        resultSet.getInt(Schema.SEQ),
                        resultSet.getInt(Schema.WAREHOUSE_ID),
                        toLocalDate(resultSet.getDate(Schema.ARRIVAL_DATE)),
                        toLocalDate(resultSet.getDate(Schema.DEPART_DATE))
                    );
                }
                catch (SQLException e) {
                    return null;
                }
            };
        
        public static final EntityMapping<ProductModelRowModel> ProductModels =
            resultSet -> new ProductModelRowModel(
                resultSet.getInt(Schema.PRODUCT_MODEL_ID),
                resultSet.getString(Schema.PRODUCT_NAME),
                resultSet.getInt(Schema.CATEGORY_ID),
                resultSet.getBigDecimal(Schema.COST),
                resultSet.getBigDecimal(Schema.MSRP)
            );
        
        public static final EntityMapping<CategoryRowModel> Categories =
            resultSet -> new CategoryRowModel(
                resultSet.getInt(Schema.CATEGORY_ID),
                resultSet.getString(Schema.CATEGORY_NAME),
                resultSet.getInt(Schema.STORAGE_SIZE)
            );
    
        public static final EntityMapping<ProductModelQuantityDto> ProductQuantities =
            resultSet -> new ProductModelQuantityDto(
                resultSet.getInt(Schema.PRODUCT_MODEL_ID),
                resultSet.getInt(Schema.Count))
                .SetProductModel(ProductModels.mapToEntity(resultSet))
                .SetCategory(Categories.mapToEntity(resultSet));
        
        public static final EntityMapping<WarehouseRowModel> Warehouses =
            resultSet -> {
                try {
                    return new WarehouseRowModel(
                        resultSet.getInt(Schema.WAREHOUSE_ID),
                        resultSet.getString(Schema.ADDRESS),
                        resultSet.getInt(Schema.CAPACITY)
                    );
                }
                catch (SQLException e) {
                    return null;
                }
            };
        
        public static final EntityMapping<WarehouseDto> WarehouseDtos =
            resultSet -> new WarehouseDto(
                Warehouses.mapToEntity(resultSet),
                resultSet.getInt(Schema.SpaceInUse)
            );
        
        public static final EntityMapping<UserLoginDto> UserLogins =
            resultSet -> new UserLoginDto(
                resultSet.getString(Schema.Username),
                resultSet.getString(Schema.PasswordHash),
                resultSet.getString(Schema.PasswordSalt),
                resultSet.getBoolean(Schema.IsAdmin));
    }
    
    private static class Schema {
        // ProductModels
        public static final String PRODUCT_MODEL_ID = "ProductModelId";
        public static final String PRODUCT_NAME = "ProductName";
        public static final String COST = "Cost";
        public static final String MSRP = "MSRP";
        
        // Warehouses
        public static final String WAREHOUSE_ID = "WarehouseId";
        public static final String ADDRESS = "Address";
        public static final String CAPACITY = "Capacity";
        
        // WarehouseDto
        public static final String SpaceInUse    = "SpaceInUse";
        
        // Inventory
        public static final String INVENTORY_ID = "InventoryId";
        
        // Tracking
        public static final String SEQ = "Seq";
        public static final String ARRIVAL_DATE = "ArrivalDate";
        public static final String DEPART_DATE = "DepartDate";
        
        // Categories
        public static final String CATEGORY_ID = "CategoryId";
        public static final String CATEGORY_NAME = "CategoryName";
        public static final String STORAGE_SIZE = "StorageSize";
        
        // User Logins
        public static final String Username = "Username";
        public static final String PasswordHash = "PasswordHash";
        public static final String PasswordSalt = "PasswordSalt";
        public static final String IsAdmin      = "IsAdmin";
        
        public static final String Count = "Count";
    }
}
