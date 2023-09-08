package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IWarehouseAnalyticsDao;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class WarehouseAnalyticsDao
    extends BaseDao
    implements IWarehouseAnalyticsDao {
    
    public WarehouseAnalyticsDao(String dbPath) {
        super(dbPath);
    }
    
    @Override
    public Query<WarehouseDto> GetAllSpaceInUse( ) {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.WarehouseDtos,
                "SELECT W.*, COALESCE(SpaceInUse, 0) AS SpaceInUse\n"
                + "FROM Warehouses AS W\n"
                + "LEFT JOIN (SELECT WarehouseId"
                + ", SUM(C.StorageSize) AS SpaceInUse\n"
                + "            FROM        Tracking AS T\n"
                + "            INNER JOIN  Inventory       AS I ON T.InventoryId       = I.InventoryId\n"
                + "            INNER JOIN  ProductModels   AS P ON I.ProductModelId    = P.ProductModelId\n"
                + "            INNER JOIN  Categories      AS C ON P.CategoryId        = C.CategoryId\n"
                + "            WHERE T.DepartDate IS NULL\n"
                + "            GROUP BY T.WarehouseId\n"
                + ") AS WarehouseSpaceInUse ON WarehouseSpaceInUse.WarehouseId = W.WarehouseId\n");
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public WarehouseDto GetSpaceInUseAtWarehouse(int warehouseId) {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.WarehouseDtos,
                "SELECT      W.*, SUM(C.StorageSize) AS SpaceInUse\n"
                + "FROM        Warehouses AS W\n"
                + "INNER JOIN  Tracking      AS T ON W.WarehouseId    = T.WarehouseId\n"
                + "INNER JOIN  Inventory     AS I ON T.InventoryId    = I.InventoryId\n"
                + "INNER JOIN  ProductModels AS P ON I.ProductModelId = P.ProductModelId\n"
                + "INNER JOIN  Categories    AS C ON C.CategoryId     = P.CategoryId\n"
                + "WHERE       DepartDate IS NULL AND WarehouseId = ?\n"
                + "GROUP BY    W.WarehouseId",
                new DBIntParameter(warehouseId)
                      )
                      .First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
