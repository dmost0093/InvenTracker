package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.logic.IRestrictionsManager;

public class HomeActivity
    extends Activity {
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        final boolean isAdmin = Services.get(IRestrictionsManager.class).IsAdmin();
        
        if (!isAdmin) {
            final Button adminButton = findViewById(R.id.buttonAdmin);
            adminButton.setVisibility(
                View.GONE
            );
        }
    }
    
    public void buttonSearch(final View v)
    {
        startActivity(new Intent(this, ProductSearchActivity.class));
    }

    public void buttonAdvancedSearch(final View v)
    {
        startActivity(new Intent(this, AdvancedSearchActivity.class));
    }
    
    public void buttonViewProductModels(final View v)
    {
        startActivity(new Intent(this, SearchResultsActivity.class));
    }
    
    public void buttonViewCategories(final View v) {
        startActivity(new Intent(this, ViewCategoriesActivity.class));
    }
    
    public void buttonViewWarehouse(final View v)
    {
        startActivity(new Intent(this, WarehouseActivity.class));
    }
    
    public void buttonAdmin(final View v) {
        startActivity(new Intent(this, AdminHomeActivity.class));
    }
    
    public void buttonAccount(final View v)
    {
        startActivity(new Intent(this, AccountActivity.class));
    }
}
