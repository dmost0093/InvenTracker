package comp3350.inventracker.daos.hsqldb;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Connection;

import comp3350.inventracker.daos.hsqldb.utils.DBBigDecimalParameter;
import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IProductModelDao;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class ProductModelDao
    extends BaseDao
    implements IProductModelDao {
    
    public ProductModelDao(final String dbPath) {super(dbPath);}
    
    @Override
    public int Insert(String name, BigDecimal cost, int categoryId,  BigDecimal MSRP)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Update(
                conn,
                "INSERT INTO ProductModels VALUES(DEFAULT, ?,?,?,?)",
                new DBStringParameter(name),
                new DBBigDecimalParameter(cost),
                new DBIntParameter(categoryId),
                new DBBigDecimalParameter(MSRP));
            
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public Query<ProductModelRowModel> SelectAll()
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.ProductModels,
                "SELECT * FROM ProductModels"
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public ProductModelRowModel SelectPk(int productId)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.ProductModels,
                "SELECT * FROM ProductModels WHERE ProductModelId = ?",
                new DBIntParameter(productId)
            ).First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
