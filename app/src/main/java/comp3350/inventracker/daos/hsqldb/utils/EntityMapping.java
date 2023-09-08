package comp3350.inventracker.daos.hsqldb.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityMapping<T> {
    T mapToEntity(ResultSet resultSet)
        throws SQLException;
}
