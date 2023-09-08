package comp3350.inventracker.daos.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
    protected final String dbPath;
    
    public BaseDao(final String dbPath) {
        this.dbPath = dbPath;
    }
    
    protected Connection connection()
        throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
}



