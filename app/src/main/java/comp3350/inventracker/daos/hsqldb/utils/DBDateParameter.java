package comp3350.inventracker.daos.hsqldb.utils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class DBDateParameter
    implements DBParameter {
    final LocalDate value;
    
    public DBDateParameter(LocalDate value) {
        this.value = value;
    }
    
    @Override
    public void addParameter(int index, PreparedStatement preparedStatement)
        throws SQLException {
        preparedStatement.setDate(
            index,
            value == null
                ? null
                : new Date(Date.from(value.atStartOfDay(ZoneId.systemDefault())
                                          .toInstant())
                               .getTime())
        );
    }
}
