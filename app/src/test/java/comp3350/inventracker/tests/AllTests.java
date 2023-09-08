package comp3350.inventracker.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    UnitTests.class,
    DBUnitTests.class,
    IntegrationTests.class,
})
public class AllTests {}
