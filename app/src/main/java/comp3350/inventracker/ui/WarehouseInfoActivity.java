package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.dtos.WarehouseDto;

public class WarehouseInfoActivity extends Activity {
    private WarehouseDto selectedWarehouse;
    // Text View
    private TextView warehouseLocation;
    private TextView warehouseCapacity;
    private TextView warehouseSpaceInUse;
    private TextView warehousePercentFull;
    
    private ProgressBar warehouseProgressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_info);
        
        selectedWarehouse = (WarehouseDto) getIntent().getSerializableExtra("selectedWarehouse");
        warehouseLocation    = findViewById(R.id.WarehouseLocation);
        warehouseCapacity    = findViewById(R.id.WarehouseCapacity);
        warehouseSpaceInUse  = findViewById(R.id.WarehouseSpaceInUse);
        warehouseProgressBar = findViewById(R.id.warehousePercentProgressBar);
        warehousePercentFull = findViewById(R.id.WarehousePercentFull);
            
        updateWarehouseInfo();
            
        Button updateButton  = findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(view -> {
            Intent intent = new Intent(WarehouseInfoActivity.this, EditWarehouseActivity.class);
            intent.putExtra("selectedWarehouse", selectedWarehouse);
            startActivity(intent);
        });
        
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateWarehouseInfo();
    }
    
    private void updateWarehouseInfo() {
        try {
            warehouseLocation.setText(selectedWarehouse.Warehouse.Address);
            warehouseCapacity.setText(String.valueOf(selectedWarehouse.Warehouse.Capacity));
            warehouseSpaceInUse.setText(String.valueOf(selectedWarehouse.SpaceInUse));
            double percent = selectedWarehouse.getPercentInUse() * 100;
            warehousePercentFull.setText(String.format(Locale.CANADA, "%.2f%%", percent));
            warehouseProgressBar.setProgress((int) percent);
        } catch (final Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
}