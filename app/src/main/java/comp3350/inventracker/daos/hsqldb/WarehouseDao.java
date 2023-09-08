package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IWarehouseDao;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class WarehouseDao
    extends BaseDao
    implements IWarehouseDao {
    
    public WarehouseDao(String dbPath) {
        super(dbPath);
    }
    
    @Override
    public int Insert(String address, int capacity)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Update(
                conn,
                "INSERT INTO Warehouses VALUES(DEFAULT, ?, ?)",
                new DBStringParameter(address),
                new DBIntParameter(capacity)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public int Update(int warehouseId, int capacity) {
        try (Connection conn = connection()) {
            return SQL.Update(
                conn,
                "UPDATE Warehouses\n"
                + "SET Capacity = ?\n"
                + "WHERE WarehouseId = ?",
                new DBIntParameter(capacity),
                new DBIntParameter(warehouseId)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public Query<WarehouseRowModel> SelectAll() {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Warehouses,
                "SELECT * FROM Warehouses"
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public WarehouseRowModel SelectPK(int warehouseId) {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Warehouses,
                "SELECT * FROM Warehouses WHERE WarehouseID = ?",
                new DBIntParameter(warehouseId)
            ).First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}

