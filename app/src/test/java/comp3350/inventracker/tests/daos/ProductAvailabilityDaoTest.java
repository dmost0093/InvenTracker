package comp3350.inventracker.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import comp3350.inventracker.daos.hsqldb.ProductAvailabilityDao;
import comp3350.inventracker.daos.IProductAvailabilityDao;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.tests.utils.TestUtils;
import comp3350.inventracker.utils.Query;

public class ProductAvailabilityDaoTest {
    private IProductAvailabilityDao availabilityDao;
    private File                    tempDB;
    
    @Before
    public void setUp()
        throws IOException {
        this.tempDB     = TestUtils.copyDB();
        availabilityDao = new ProductAvailabilityDao(this.tempDB.getAbsolutePath().replace(".script", ""));
    }
    
    @After
    public void tearDown() {
        assertTrue("testing error: tempDB should be deleted after tests have run.", this.tempDB.delete());
    }
    
    @Test
    public void testGetAvailableProductModelQuantities() {
        // Act
        Query<ProductModelQuantityDto> quantities = availabilityDao.getAvailableProductModelQuantities();
        
        // Assert
        assertNotNull(quantities);
        quantities.ToList().forEach(x -> assertTrue(x.Quantity > 0));
    
        quantities.ToList().forEach(x -> {
            assertNotNull(x.ProductModel);
            assertNotNull(x.Category);
        });
    }
    
    @Test
    public void testGetAllProductModelQuantities() {
        // Act
        Query<ProductModelQuantityDto> quantities = availabilityDao.getAllProductModelQuantities();
    
        // Assert
        assertNotNull(quantities);
        assertTrue(quantities.ToList().stream().anyMatch(x -> x.Quantity == 0));
    
        quantities.ToList().forEach(x -> {
            assertNotNull(x.ProductModel);
            assertNotNull(x.Category);
        });
    }
    
    @Test
    public void testGetAvailableItemsByProductModel() {
        final LocalDate date = LocalDate.of(2023, 6, 2);
        final int productModelId = 1;
        
        // Act
        Query<CompleteItemDto> items = availabilityDao.getAvailableItemsByProductModel(productModelId, date);
    
        // Assert
        assertNotNull(items);
        items.ToList().forEach(x -> {
            assertEquals(productModelId, x.ProductModelRowModel.ProductModelId);
    
            assertNotNull(x.ProductModelRowModel);
            assertNotNull(x.CategoryRowModel);
            assertNotNull(x.InventoryRowModel);
            assertNotNull(x.WarehouseRowModel);
            assertNotNull(x.TrackingRowModel);
        });
    }
}