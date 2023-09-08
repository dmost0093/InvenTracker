package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBBooleanParameter;
import comp3350.inventracker.daos.hsqldb.utils.SQL;
import comp3350.inventracker.daos.IUserLoginsDao;
import comp3350.inventracker.dtos.UserLoginDto;
import comp3350.inventracker.exceptions.PersistenceException;

public class UserLoginsDao
    extends BaseDao
    implements IUserLoginsDao {
    
    public UserLoginsDao(String dbPath) {
        super(dbPath);
    }
    
    @Override
    public UserLoginDto Select(String username)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Get(
                conn,
                DBSchema.MapSQLTo.UserLogins,
                "SELECT * FROM UserLogins WHERE Username = ?",
                new DBStringParameter(username)
            ).First();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public int Insert(UserLoginDto dto)
        throws PersistenceException {
        try (Connection conn = connection()) {
            return SQL.Update(
                conn,
                "INSERT INTO UserLogins VALUES(?, ?, ?, ?)",
                new DBStringParameter(dto.Username),
                new DBStringParameter(dto.PasswordHash),
                new DBStringParameter(dto.PasswordSalt),
                new DBBooleanParameter(dto.IsAdmin != null ? dto.IsAdmin : false)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public void UpdatePassword(UserLoginDto dto)
        throws PersistenceException {
        try (Connection conn = connection()) {
            SQL.Update(
                conn,
                "UPDATE UserLogins\n"
                + "SET PasswordHash = ?, PasswordSalt = ?\n"
                + "WHERE Username = ?\n",
                new DBStringParameter(dto.PasswordHash),
                new DBStringParameter(dto.PasswordSalt),
                new DBStringParameter(dto.Username)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    @Override
    public void DeleteUser(String username)
        throws PersistenceException {
        try (Connection conn = connection()) {
            SQL.Update(
                conn,
                "DELETE FROM UserLogins\n"
                + "WHERE Username = ?\n",
                new DBStringParameter(username)
            );
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
}
