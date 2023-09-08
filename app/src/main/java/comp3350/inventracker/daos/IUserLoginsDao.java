package comp3350.inventracker.daos;

import comp3350.inventracker.exceptions.PersistenceException;
import comp3350.inventracker.dtos.UserLoginDto;

public interface IUserLoginsDao {
    int Insert(UserLoginDto dto)
        throws PersistenceException;
    
    UserLoginDto Select(String username)
        throws PersistenceException;
    
    void UpdatePassword(UserLoginDto dto)
        throws PersistenceException;
    void DeleteUser(String username)
        throws PersistenceException;
}
