package comp3350.inventracker.daos.hsqldb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import comp3350.inventracker.daos.hsqldb.utils.DBParameter;

public class DBStringParameter
    implements DBParameter {
    final String value;
    
    public DBStringParameter(String value) {
        this.value = value;
    }
    
    @Override
    public void addParameter(int index, PreparedStatement preparedStatement)
        throws SQLException {
        preparedStatement.setString(index, value);
    }
}
