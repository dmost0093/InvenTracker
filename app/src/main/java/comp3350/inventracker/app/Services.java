package comp3350.inventracker.app;

import static comp3350.inventracker.app.Repositories.*;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import comp3350.inventracker.logic.impl.CategoriesManager;
import comp3350.inventracker.logic.impl.CategoryEditManager;
import comp3350.inventracker.logic.ICategoriesManager;
import comp3350.inventracker.logic.ICategoryEditManager;
import comp3350.inventracker.logic.IInventoryLogisticsManager;
import comp3350.inventracker.logic.IAccountCreationUtility;
import comp3350.inventracker.logic.IPasswordEncryptionManager;
import comp3350.inventracker.logic.IPasswordUpdateManager;
import comp3350.inventracker.logic.IProductModelManager;
import comp3350.inventracker.logic.IProductSearchResultsManager;
import comp3350.inventracker.logic.IRegistrationManager;
import comp3350.inventracker.logic.IRestrictionsManager;
import comp3350.inventracker.logic.IUserLoginManager;
import comp3350.inventracker.logic.IWarehouseAnalyticsManager;
import comp3350.inventracker.logic.IWarehouseManager;
import comp3350.inventracker.logic.impl.InventoryLogisticsManager;
import comp3350.inventracker.logic.impl.AccountCreationUtility;
import comp3350.inventracker.logic.impl.PasswordEncryptionManager;
import comp3350.inventracker.logic.impl.PasswordUpdateManager;
import comp3350.inventracker.logic.impl.ProductModelManager;
import comp3350.inventracker.logic.impl.ProductSearchManager;
import comp3350.inventracker.logic.impl.ProductSearchResultsManager;
import comp3350.inventracker.logic.impl.RegistrationManager;
import comp3350.inventracker.logic.impl.RestrictionsManager;
import comp3350.inventracker.logic.impl.UserLoginManager;
import comp3350.inventracker.logic.impl.WarehouseAnalyticsManager;
import comp3350.inventracker.logic.impl.WarehouseManager;

public class Services {
    
    public static <T> T get(Class<T> clazz) {
        Method method = Arrays.stream(Services.class.getDeclaredMethods())
                              .filter(m -> clazz.isAssignableFrom(m.getReturnType())
                                           && m.getParameterCount() == 0)
                              .findFirst()
                              .orElse(null);
        
        if (method == null) {
            throw new RuntimeException(String.format("No suitable factory method for %s was found.",
                                                     clazz.getCanonicalName()));
        }
        
        try {
            return clazz.cast(method.invoke(Services.class));
        }
        catch (Exception e) {
            throw new RuntimeException(
                String.format("Error invoking factory method: '%s' for Class: '%s'",
                              method.getName(),
                              clazz.getCanonicalName()));
        }
    }
    
    // ------------------------------------------------------
    //
    // FACTORY METHODS BELOW
    //
    // ------------------------------------------------------
    
    private static IInventoryLogisticsManager getInventoryLogistics() {
        return new InventoryLogisticsManager(getInventoryLogisticsDao());
    }
    
    private static ICategoryEditManager getCategoryEditManager() {
        return new CategoryEditManager(getCategoriesDao());
    }
    
    private static ICategoriesManager getCategoriesManager() {
        return new CategoriesManager(getCategoriesDao());
    }
    
    private static IWarehouseManager getWarehouseManager() {
        return new WarehouseManager(getWarehouseDao());
    }
    
    private static IWarehouseAnalyticsManager getWarehouseAnalyticsManager() {
        return new WarehouseAnalyticsManager(getWarehouseAnalyticsDao());
    }
    
    private static ProductSearchManager getProductSearchManager() {
        return new ProductSearchManager(
            getProductsDao(),
            getWarehouseDao(),
            getCategoriesDao()
        );
    }
    
    private static IProductModelManager getProductModelManager() {
        return new ProductModelManager(
            Repositories.getProductModelDao(),
            Repositories.getCategoriesDao());
    }
    
    private static IProductSearchResultsManager getProductSearchResultsManager() {
        return new ProductSearchResultsManager(
            getProductSearchDao()
        );
    }
    
    private static IRestrictionsManager getRestrictionsManager() {
        return new RestrictionsManager();
    }
    
    private static IUserLoginManager getLoginManager() {
        return new UserLoginManager(
            getUserLoginsDao(),
            getPasswordEncryptionManager(),
            getRestrictionsManager()
        );
    }
    
    private static IRegistrationManager getRegistrationManager() {
        return new RegistrationManager(getUserLoginsDao(), getNewUserUtility());
    }
    
    private static IPasswordUpdateManager getPasswordUpdateManager() {
        return new PasswordUpdateManager(getUserLoginsDao(), getLoginManager(), getNewUserUtility());
    }
    
    private static IAccountCreationUtility getNewUserUtility() {
        return new AccountCreationUtility(getPasswordEncryptionManager());
    }
    
    private static IPasswordEncryptionManager getPasswordEncryptionManager() {
        return new PasswordEncryptionManager(getEncryptionAlgorithm());
    }
    
    private static MessageDigest getEncryptionAlgorithm() {
        try {
            // Get an instance of the SHA-256 message digest algorithm
            return MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
