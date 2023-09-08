package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import comp3350.inventracker.daos.hsqldb.utils.DBDateParameter;
import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IProductAvailabilityDao;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class ProductAvailabilityDao
    extends BaseDao
    implements IProductAvailabilityDao {
    
    public ProductAvailabilityDao(String dbPath) {
        super(dbPath);
    }
    
    @Override
    public Query<ProductModelQuantityDto> getAvailableProductModelQuantities() {
        return getAllProductModelQuantities().Where(x -> x.Quantity > 0);
    }
    
    @Override
    public Query<ProductModelQuantityDto> getAllProductModelQuantities() {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.ProductQuantities,
                "SELECT  P.*, C.*, COALESCE(ITCount.Count, 0) AS Count\n"
                + "FROM        ProductModels AS P\n"
                + "INNER JOIN  Categories AS C     ON P.CategoryId         = C.CategoryId\n"
                + "LEFT JOIN ( SELECT      I.ProductModelId, COUNT(*) AS Count\n"
                + "            FROM        Inventory AS I\n"
                + "            INNER JOIN  Tracking AS T ON I.InventoryId  = T.InventoryId\n"
                + "            WHERE       T.DepartDate IS NULL\n"
                + "            GROUP BY    I.ProductModelId\n"
                + ") AS ITCount ON P.ProductModelId = ITCount.ProductModelId"
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public Query<CompleteItemDto> getAvailableItemsByProductModel(int productModelId, LocalDate asOfDate)
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
}
