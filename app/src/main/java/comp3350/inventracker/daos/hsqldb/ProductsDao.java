package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IProductsDao;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.exceptions.PersistenceException;

public class ProductsDao
    extends BaseDao
    implements IProductsDao {
    
    public ProductsDao(String dbPath) {
        super(dbPath);
    }
    
    public ProductModelRowModel SelectPK(int productModelId)
        throws PersistenceException {
    
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.ProductModels,
                "SELECT * FROM ProductModels WHERE ProductModelId = ?",
                new DBIntParameter(productModelId)
            ).First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}