package comp3350.inventracker.tests.daos;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import comp3350.inventracker.daos.hsqldb.InventoryDao;
import comp3350.inventracker.daos.IInventoryDao;
import comp3350.inventracker.dtos.InventoryRowModel;
import comp3350.inventracker.tests.utils.TestUtils;
import comp3350.inventracker.utils.Query;

public class InventoryDaoTest {
    private IInventoryDao inventoryDao;
    private File          tempDB;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB  = TestUtils.copyDB();
        inventoryDao = new InventoryDao(this.tempDB.getAbsolutePath().replace(".script", ""));
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }

    @Test
    public void testSelectAll() {
        // Act
        Query<InventoryRowModel> selectAll = inventoryDao.SelectAll();
    
        // Assert
        assertNotNull(selectAll);
        selectAll.ToList().forEach(x -> {
            assertTrue(x.InventoryId > 0);
            assertTrue(x.ProductModelId > 0);
        });
    }
}