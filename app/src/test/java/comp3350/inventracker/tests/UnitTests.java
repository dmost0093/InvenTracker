package comp3350.inventracker.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.inventracker.tests.logic.CategoriesManagerTest;
import comp3350.inventracker.tests.logic.CategoryEditManagerTest;
import comp3350.inventracker.tests.logic.FilterTest;
import comp3350.inventracker.tests.logic.InventoryLogisticsManagerTest;
import comp3350.inventracker.tests.logic.AccountCreationUtilityTest;
import comp3350.inventracker.tests.logic.PasswordEncryptionManagerTest;
import comp3350.inventracker.tests.logic.PasswordUpdateManagerTest;
import comp3350.inventracker.tests.logic.ProductModelManagerTest;
import comp3350.inventracker.tests.logic.ProductSearchManagerTest;
import comp3350.inventracker.tests.logic.ProductSearchResultsManagerTest;
import comp3350.inventracker.tests.logic.RegistrationManagerTest;
import comp3350.inventracker.tests.logic.AccountValidationUtilityTest;
import comp3350.inventracker.tests.logic.RestrictionsManagerTest;
import comp3350.inventracker.tests.logic.UserLoginManagerTest;
import comp3350.inventracker.tests.logic.WarehouseAnalyticsManagerTest;
import comp3350.inventracker.tests.logic.WarehouseManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CategoriesManagerTest.class,
    CategoryEditManagerTest.class,
    WarehouseAnalyticsManagerTest.class,
    WarehouseManagerTest.class,
    UserLoginManagerTest.class,
    PasswordEncryptionManagerTest.class,
    AccountCreationUtilityTest.class,
    AccountValidationUtilityTest.class,
    RestrictionsManagerTest.class,
    RegistrationManagerTest.class,
    PasswordUpdateManagerTest.class,
    InventoryLogisticsManagerTest.class,
    FilterTest.class,
    ProductSearchResultsManagerTest.class,
    ProductSearchManagerTest.class,
    ProductModelManagerTest.class,
})
public class UnitTests {}
