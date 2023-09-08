package comp3350.inventracker.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.inventracker.tests.logic.CategoriesIT;
import comp3350.inventracker.tests.logic.NewUserIT;
import comp3350.inventracker.tests.logic.UserLoginIT;
import comp3350.inventracker.tests.logic.WarehouseAnalyticsIT;
import comp3350.inventracker.tests.logic.WarehouseIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CategoriesIT.class,
    UserLoginIT.class,
    WarehouseAnalyticsIT.class,
    WarehouseIT.class,
    NewUserIT.class,
})
public class IntegrationTests {
}
