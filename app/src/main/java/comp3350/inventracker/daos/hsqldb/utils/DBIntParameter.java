package comp3350.inventracker.daos.hsqldb.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBIntParameter
    implements DBParameter {
    final int value;
    
    public DBIntParameter(int value) {
        this.value = value;
    }
    
    @Override
    public void addParameter(int index, PreparedStatement preparedStatement)
        throws SQLException {
        preparedStatement.setInt(index, value);
    }
}
