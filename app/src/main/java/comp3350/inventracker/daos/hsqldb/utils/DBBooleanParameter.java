package comp3350.inventracker.daos.hsqldb.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBBooleanParameter
    implements DBParameter {
    final boolean value;
    
    public DBBooleanParameter(boolean value) {
        this.value = value;
    }
    
    @Override
    public void addParameter(int index, PreparedStatement preparedStatement)
        throws SQLException {
        preparedStatement.setBoolean(index, value);
    }
}
