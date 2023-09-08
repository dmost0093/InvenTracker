package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.InventoryItemDto;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.logic.ICategoriesManager;
import comp3350.inventracker.logic.IProductSearchResultsManager;

public class ProductModelItemsActivity
    extends Activity {
    private ProductModelRowModel   productModel;
    private Filter                 filter;
    
    final private List<InventoryItemDto> items = new ArrayList<>();
    private InventoryDtoArrayAdapter     arrayAdapter;
    private IProductSearchResultsManager search;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_model_inventory);
    
        productModel = (ProductModelRowModel) getIntent().getSerializableExtra("productModel");
        filter       = (Filter) getIntent().getSerializableExtra("filter");
        
        final CategoryRowModel category = Services.get(ICategoriesManager.class)
                                                  .GetCategoryById(productModel.CategoryId);
        
        TextView productCategory   = findViewById(R.id.productCategory);
        TextView productModelName  = findViewById(R.id.productModelName);
        TextView productSize       = findViewById(R.id.productSize);
        TextView modelNumber       = findViewById(R.id.modelNumber);
    
        productCategory.setText(category.CategoryName);
        productModelName.setText(productModel.Name);
        productSize.setText(String.format(Locale.CANADA, "%d", category.StorageSize));
        modelNumber.setText(String.format(Locale.CANADA, "%04d", productModel.ProductModelId));
    
   
        // Setup the ListView
        search       = Services.get(IProductSearchResultsManager.class);
        arrayAdapter = new InventoryDtoArrayAdapter(this, items);
    
        final ListView listView = findViewById(R.id.listViewInventoryForProductModel);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this::onItemClickListener);
        
        update();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
    
    private void update() {
        if (productModel == null)
            return;
    
        try {
            // update the ListView
            items.clear();
            
            items.addAll(search.GetAvailableItems(productModel.ProductModelId, LocalDate.now(), filter));
            arrayAdapter.notifyDataSetChanged();
    
            // Display the quantity available
            int quantity = items.size();
            TextView availableQuantity = findViewById(R.id.availableQuantity);
            availableQuantity.setText(String.format(Locale.CANADA, "%d", quantity));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void buttonAddInventory(View v) {
        startActivity(new Intent(this, AddInventoryActivity.class).putExtra("productModel", productModel));
    }
    
    private void onItemClickListener(AdapterView<?> ignoredParent, View ignoredView, int position, long ignoredId) {
        Intent intent = new Intent(this, ViewItemActivity.class);
        
        InventoryItemDto item = items.get(position);
    
        startActivity(intent.putExtra("inventoryId", item.InventoryRow.InventoryId));
    }
}
