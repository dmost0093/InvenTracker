package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import comp3350.inventracker.R;

public class AdminHomeActivity
    extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }
    
    public void buttonAddWarehouse(View v)
    {
        Intent intent = new Intent(AdminHomeActivity.this, AddWarehouseActivity.class);
        AdminHomeActivity.this.startActivity(intent);
    }
    
    public void buttonAddCategory(View v)
    {
        Intent intent = new Intent(AdminHomeActivity.this, AddCategoryActivity.class);
        AdminHomeActivity.this.startActivity(intent);
    }
    
    public void buttonAddProductModel(View v)
    {
        Intent intent = new Intent(AdminHomeActivity.this, AddProductModelActivity.class);
        AdminHomeActivity.this.startActivity(intent);
    }
    public void buttonAddUser(View v)
    {
        Intent intent = new Intent(AdminHomeActivity.this, RegistrationActivity.class);
        AdminHomeActivity.this.startActivity(intent);
    }
}
