package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import comp3350.inventracker.daos.hsqldb.utils.DBDateParameter;
import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IProductSearchDao;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class ProductSearchDao
    extends BaseDao
    implements IProductSearchDao {
    
    public ProductSearchDao(String dbPath) {
        super(dbPath);
    }
    
    public Query<CompleteItemDto> GetAllAvailableItems(LocalDate asOfDate)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.CompleteItem,
                "SELECT  *\n"
                + "FROM        ProductModels AS P\n"
                + "LEFT JOIN  Inventory   AS I ON P.ProductModelId = I.ProductModelId \n"
                + "LEFT JOIN  Categories  AS C ON P.CategoryId     = C.CategoryId\n"
                + "LEFT JOIN ( SELECT *\n"
                + "            FROM Tracking\n"
                + "            WHERE DepartDate IS NULL AND ArrivalDate <= ?\n"
                + "            )           AS T ON I.InventoryId    = T.InventoryId\n"
                + "LEFT JOIN  Warehouses   AS W ON T.WarehouseId    = W.WarehouseId",
                new DBDateParameter(asOfDate)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public Query<CompleteItemDto> GetAvailableItems(int productModelId, LocalDate asOfDate)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.CompleteItem,
                "SELECT  *\n"
                + "FROM        Inventory AS I\n"
                + "INNER JOIN  ProductModels AS P ON I.ProductModelId = P.ProductModelId \n"
                + "INNER JOIN  Categories AS C ON P.CategoryId = C.CategoryId\n"
                + "INNER JOIN  Tracking AS T ON I.InventoryId = T.InventoryId\n"
                + "INNER JOIN  Warehouses AS W ON T.WarehouseId = W.WarehouseId\n"
                + "WHERE I.ProductModelId = ? AND T.DepartDate IS NULL AND T.ArrivalDate <= ?",
                new DBIntParameter(productModelId),
                new DBDateParameter(asOfDate)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public CompleteItemDto GetAvailableItem(int inventoryId)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.CompleteItem,
                "SELECT  *\n"
                + "FROM        Inventory AS I\n"
                + "INNER JOIN  ProductModels AS P ON I.ProductModelId = P.ProductModelId \n"
                + "INNER JOIN  Categories AS C ON P.CategoryId = C.CategoryId\n"
                + "INNER JOIN  Tracking AS T ON I.InventoryId = T.InventoryId\n"
                + "INNER JOIN  Warehouses AS W ON T.WarehouseId = W.WarehouseId\n"
                + "WHERE I.InventoryId = ? AND T.DepartDate IS NULL",
                new DBIntParameter(inventoryId)
            ).First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
