package comp3350.inventracker.daos.hsqldb.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBParameter {
    void addParameter(int index, PreparedStatement preparedStatement)
        throws SQLException;
}
