package comp3350.inventracker.daos.hsqldb.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.utils.Query;

public class SQL {
    public static <T> Query<T> Get(
        Connection conn,
        EntityMapping<T> entityMapping,
        String sql,
        DBParameter... dbParameters
    ) throws PersistenceException {
        try {
            final List<T> results = new ArrayList<>();
            
            final PreparedStatement statement = conn.prepareStatement(sql);
            for (int i = 0; i < dbParameters.length; i++) {
                dbParameters[i].addParameter(i+1, statement);
            }
            
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(entityMapping.mapToEntity(resultSet));
            }
            
            resultSet.close();
            statement.close();
            
            return new Query<>(results);
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }
    
    public static <T> int Update(
        Connection conn,
        String sql,
        DBParameter... dbParameters
    ) throws PersistenceException {
        try {
            final PreparedStatement statement = conn.prepareStatement(sql);
            
            for (int i = 0; i < dbParameters.length; i++) {
                dbParameters[i].addParameter(i+1, statement);
            }
            
            return statement.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            throw new PersistenceException.IntegrityConstraintViolation(e);
        }
        catch (final SQLException e)
        {
            throw new PersistenceException(e);
        }
    }
}

