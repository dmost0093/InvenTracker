package comp3350.inventracker.daos.hsqldb.utils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBBigDecimalParameter
    implements DBParameter {
    final BigDecimal value;
    
    public DBBigDecimalParameter(BigDecimal value) {
        this.value = value;
    }
    
    @Override
    public void addParameter(int index, PreparedStatement preparedStatement)
        throws SQLException {
        preparedStatement.setBigDecimal(index, value);
    }
}
