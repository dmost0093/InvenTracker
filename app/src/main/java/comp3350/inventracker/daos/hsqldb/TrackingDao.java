package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.ITrackingDao;
import comp3350.inventracker.dtos.TrackingRowModel;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class TrackingDao
    extends BaseDao
    implements ITrackingDao {
    public TrackingDao(String dbPath) {
        super(dbPath);
    }
    
    @Override
    public Query<TrackingRowModel> SelectAll() {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Tracking,
                "SELECT * FROM Tracking"
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public Query<TrackingRowModel> GetInventoryPath(int inventoryId)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Tracking,
                "SELECT * FROM Tracking\n"
                + "WHERE InventoryId = ?\n"
                + "ORDER BY Seq",
                new DBIntParameter(inventoryId)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
