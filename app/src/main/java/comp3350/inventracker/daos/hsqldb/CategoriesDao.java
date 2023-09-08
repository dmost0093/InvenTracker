package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBIntParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.ICategoriesDao;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class CategoriesDao
    extends BaseDao
    implements ICategoriesDao {
    
    public CategoriesDao(final String dbPath) {
        super(dbPath);
    }
    
    @Override
    public Query<CategoryRowModel> SelectAll()
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Categories,
                "SELECT * FROM Categories"
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public CategoryRowModel SelectPK(int categoryId)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.Categories,
                "SELECT * FROM Categories WHERE CategoryId = ?",
                new DBIntParameter(categoryId)
            ).First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public int Insert(String categoryName, int storageSize)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Update(
                conn,
                "INSERT INTO Categories VALUES(DEFAULT, ?, ?)",
                new DBStringParameter(categoryName),
                new DBIntParameter(storageSize));
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    public int Update(int categoryId, String newName)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Update(
                conn,
                "UPDATE Categories SET CategoryName = ? WHERE CategoryId = ?",
                new DBStringParameter(newName),
                new DBIntParameter(categoryId)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
