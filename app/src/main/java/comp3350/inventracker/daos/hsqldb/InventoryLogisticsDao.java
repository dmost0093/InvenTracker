package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import comp3350.inventracker.daos.hsqldb.utils.DBDateParameter;
import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IInventoryLogisticsDao;
import comp3350.inventracker.exceptions.PersistenceException;

public class InventoryLogisticsDao
    extends BaseDao
    implements IInventoryLogisticsDao {
    
    public InventoryLogisticsDao(String dbPath) {
        super(dbPath);
    }

    @Override
    public void ImportInventory(int productModelId, int toWarehouseId, LocalDate asOfDate) {
        try (Connection conn = connection()) {
            SQL.Update(
                conn,
                "INSERT INTO Inventory VALUES(DEFAULT, ?);\n",
                new DBIntParameter(productModelId)
            );
    
            SQL.Update(
                conn,
                "INSERT INTO Tracking VALUES((SELECT MAX(InventoryId) FROM Inventory), 0, ?, ?, NULL)",
                new DBIntParameter(toWarehouseId),
                new DBDateParameter(asOfDate)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    @Override
    public void TransportInventory(int inventoryId, int toWarehouseId, LocalDate asOfDate) {
        try (Connection conn = connection()) {
            int rowsUpdated = SQL.Update(
                conn,
                "UPDATE Tracking\n"
                + "SET DepartDate = ?\n"
                + "WHERE InventoryId = ? AND DepartDate IS NULL",
                new DBDateParameter(asOfDate),
                new DBIntParameter(inventoryId)
            );
            
            if (rowsUpdated == 0) {
                return;
            }
    
            SQL.Update(
                conn,
                "INSERT INTO Tracking VALUES(?, (SELECT MAX(Seq) + 1 FROM Tracking WHERE InventoryId = ?), ?, ?, NULL)",
                new DBIntParameter(inventoryId),
                new DBIntParameter(inventoryId),
                new DBIntParameter(toWarehouseId),
                new DBDateParameter(asOfDate)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public void ExportInventory(int inventoryId, LocalDate asOfDate) {
        try (Connection conn = connection()) {
            SQL.Update(
                conn,
                "UPDATE Tracking\n"
                + "SET DepartDate = ?\n"
                + "WHERE InventoryId = ? AND DepartDate IS NULL",
                new DBDateParameter(asOfDate),
                new DBIntParameter(inventoryId)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}