package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.time.LocalDate;
import java.util.List;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.Filter;
import comp3350.inventracker.dtos.ProductModelQuantityDto;
import comp3350.inventracker.dtos.SortingOption;
import comp3350.inventracker.logic.IProductSearchResultsManager;

public class SearchResultsActivity
    extends Activity {
    private List<ProductModelQuantityDto> filteredList;
    private Filter filter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        
        filter = (Filter) getIntent().getSerializableExtra("filter");
        SortingOption sortBy = (SortingOption) getIntent().getSerializableExtra("sortBy");
    
        IProductSearchResultsManager searchResultsManager = Services.get(IProductSearchResultsManager.class);
        filteredList = searchResultsManager.GetAvailableQuantities(LocalDate.now(), filter, sortBy);
        
        try {
            final ListView listView = findViewById(R.id.listViewProductModelQuantities);
            listView.setAdapter(new ProductQuantitiesArrayAdapter(this, filteredList));
            listView.setOnItemClickListener(this::onItemClick);
        }
        catch (final Exception e)
        {
            Log.e("Error", e.getMessage());
        }
    }
    
    void onItemClick(AdapterView<?> ignoredParent, View ignoredView, int position, long ignoredId) {
        Intent intent = new Intent(this, ProductModelItemsActivity.class);
        
        ProductModelQuantityDto quantityDto = filteredList.get(position);
    
        intent.putExtra("productModel", quantityDto.ProductModel);
        intent.putExtra("filter", filter);
        
        startActivity(intent);
    }
}
