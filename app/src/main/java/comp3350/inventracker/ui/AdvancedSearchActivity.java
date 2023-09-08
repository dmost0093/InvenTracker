package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.SortingOption;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.impl.ProductSearchManager;

public class AdvancedSearchActivity
    extends Activity {
    
    public static final String INVALID_PRICE_RANGE_MSG = "Ensure that Max is no less than Min.";
    private Spinner warehouseDropdown;
    private Spinner  categoryDropdown;
    private Spinner  sortByDropdown;
    private EditText minPriceInput;
    private EditText maxPriceInput;
    private TextView errorText;
    private CheckBox outOfStockCheckbox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
    
        warehouseDropdown  = findViewById(R.id.warehouseDropdown);
        categoryDropdown   = findViewById(R.id.categoryDropdown);
        sortByDropdown     = findViewById(R.id.sortByDropdown);
        minPriceInput      = findViewById(R.id.input_minMSRP);
        maxPriceInput      = findViewById(R.id.input_maxMSRP);
        errorText          = findViewById(R.id.errorMessage);
        outOfStockCheckbox = findViewById(R.id.checkboxOutOfStock);
        
        ProductSearchManager productSearchManager = Services.get(ProductSearchManager.class);
    
        warehouseDropdown.setAdapter(new WarehouseArrayAdapter(
            this,
            productSearchManager.GetWarehouseOptions()
        ));
    
        categoryDropdown.setAdapter(new CategoryArrayAdapter(
            this,
            productSearchManager.GetCategoryOptions()
        ));
    
        sortByDropdown.setAdapter(new SortByArrayAdapter(
            this,
            SortingOption.values()
        ));
    }
    
    public void buttonSearch(View ignored) {
        int categoryId = 0;
        int warehouseId = 0;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;
        
        boolean includeOutOfStock = outOfStockCheckbox.isChecked();
    
        try {
            WarehouseRowModel warehouse   = (WarehouseRowModel) UIUtil.getCurrentDropdownItem(warehouseDropdown);
            if (warehouse != null) {
                warehouseId = warehouse.WarehouseID;
            }
        }
        catch (Exception ignored1) {}
    
        try {
            CategoryRowModel category = (CategoryRowModel) UIUtil.getCurrentDropdownItem(categoryDropdown);
            if (category != null) {
                categoryId = category.CategoryId;
            }
        }
        catch (Exception ignored1) {}
    
    
        try {
            String minPriceString = minPriceInput.getText().toString();
            minPrice = Integer.parseInt(minPriceString);
        }
        catch (NumberFormatException ignored1) {}
    
        try {
            String maxPriceString = maxPriceInput.getText().toString();
            maxPrice = Integer.parseInt(maxPriceString);
        }
        catch (NumberFormatException ignored1) {}
        
        if (minPrice > maxPrice) {
            errorText.setText(INVALID_PRICE_RANGE_MSG);
        }
    
        Intent intent = new Intent(this, SearchResultsActivity.class);
        Filter filter  = new Filter(categoryId, warehouseId, minPrice, maxPrice, includeOutOfStock);
        intent.putExtra("filter", filter);
        intent.putExtra("sortBy", (SortingOption) UIUtil.getCurrentDropdownItem(sortByDropdown));
        
        Log.d("", filter.toString());
        
        startActivity(intent);
    }
}
