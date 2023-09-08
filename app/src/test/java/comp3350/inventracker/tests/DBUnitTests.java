package comp3350.inventracker.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.inventracker.tests.daos.CategoryDaoTest;
import comp3350.inventracker.tests.daos.InventoryDaoTest;
import comp3350.inventracker.tests.daos.InventoryLogisticsDaoTest;
import comp3350.inventracker.tests.daos.LoginDaoTest;
import comp3350.inventracker.tests.daos.ProductAvailabilityDaoTest;
import comp3350.inventracker.tests.daos.WarehouseDaoTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CategoryDaoTest.class,
    WarehouseDaoTest.class,
    InventoryDaoTest.class,
    InventoryLogisticsDaoTest.class,
    LoginDaoTest.class,
    ProductAvailabilityDaoTest.class,
})
public class DBUnitTests {}
