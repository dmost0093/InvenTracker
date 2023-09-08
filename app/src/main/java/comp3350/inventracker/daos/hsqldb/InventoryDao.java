package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IInventoryDao;
import comp3350.inventracker.dtos.InventoryRowModel;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class InventoryDao
    extends BaseDao
    implements IInventoryDao {
    
    public InventoryDao(String dbPath) {
        super(dbPath);
    }
    
    @Override
    public Query<InventoryRowModel> SelectAll() {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Inventory,
                "SELECT * FROM Inventory"
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}