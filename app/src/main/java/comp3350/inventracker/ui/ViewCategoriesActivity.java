package comp3350.inventracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.logic.ICategoriesManager;

public class ViewCategoriesActivity
    extends Activity {
    
    private List<CategoryRowModel> categoryRowModels;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        
        categoryRowModels = Services.get(ICategoriesManager.class).GetAllCategories();
        
        try
        {
            ArrayAdapter<CategoryRowModel> inventoryArrayAdapter = new ArrayAdapter<CategoryRowModel>(
                this,
                R.layout.listitem_category,
                R.id.categoryName,
                categoryRowModels) {
                @NonNull
                @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    
                    TextView categoryName = view.findViewById(R.id.categoryName);
                    TextView storageSize  = view.findViewById(R.id.storageSize);
                    
                    CategoryRowModel categoryRowModel = categoryRowModels.get(position);
                    
                    categoryName.setText(categoryRowModel.CategoryName);
                    storageSize.setText(String.format(Locale.CANADA, "%d", categoryRowModel.StorageSize));
                    
                    return view;
                }
            };
            
            final ListView listView = findViewById(R.id.listViewCategories);
            listView.setAdapter(inventoryArrayAdapter);
        }
        catch (final Exception e)
        {
            Log.e("Error", e.getMessage());
        }
    }
}
