package comp3350.inventracker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.time.LocalDate;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.dtos.WarehouseRowModel;
import comp3350.inventracker.logic.IInventoryLogisticsManager;
import comp3350.inventracker.logic.IWarehouseManager;

public class AddInventoryActivity extends Activity {
    
    private ProductModelRowModel productModelRowModel;
    private Spinner dropdown;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        
        productModelRowModel = (ProductModelRowModel) getIntent().getSerializableExtra("productModel");
        
        WarehouseArrayAdapter warehousesAdapter = new WarehouseArrayAdapter(
            this,
            Services.get(IWarehouseManager.class)
                    .SelectAllWarehouse()
        );
        
        dropdown = findViewById(R.id.warehouseIdDropdown);
        dropdown.setAdapter(warehousesAdapter);
    }
    
    public void onSubmit(View ignored) {
        int selectedPosition = dropdown.getSelectedItemPosition();
        if (selectedPosition != AdapterView.INVALID_POSITION) {
            WarehouseRowModel selectedWarehouse = (WarehouseRowModel) dropdown.getItemAtPosition(selectedPosition);
            if (selectedWarehouse != null) {
                Services.get(IInventoryLogisticsManager.class)
                        .ImportInventory(
                            productModelRowModel.ProductModelId,
                            selectedWarehouse.WarehouseID,
                            LocalDate.now()
                        );
    
                onBackPressed();
            }
        }
    }
}