package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.CompleteItemDto;
import comp3350.inventracker.logic.IInventoryLogisticsManager;
import comp3350.inventracker.logic.IProductSearchResultsManager;

public class ViewItemActivity
    extends Activity {
    
    private TextView productModelName;
    private TextView modelNumber;
    private TextView productCategory;
    private TextView productSize;
    private TextView warehouseName;
    private TextView arrivalDate;
    private int inventoryId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
    
        inventoryId = getIntent().getIntExtra("inventoryId", 0);
        
        productModelName = findViewById(R.id.productModelName);
        modelNumber      = findViewById(R.id.modelNumber);
        productCategory  = findViewById(R.id.productCategory);
        productSize      = findViewById(R.id.productSize);
        warehouseName    = findViewById(R.id.warehouseNameValue);
        arrivalDate      = findViewById(R.id.arrivalDateValue);

        update();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
    
    public void buttonRemoveInventory(View v) {
        Services.get(IInventoryLogisticsManager.class).ExportInventory(inventoryId, LocalDate.now());
        
        onBackPressed();
    }
    
    public void buttonMoveWarehouse(View v) {
        Intent intent = new Intent(this, MoveInventoryActivity.class);
        intent.putExtra("inventoryId", inventoryId);
        startActivity(intent);
    }
    
    private void update() {
        CompleteItemDto dto = Services.get(IProductSearchResultsManager.class)
                                      .GetAvailableItem(inventoryId);
    
        productModelName.setText(dto.ProductModelRowModel.Name);
        modelNumber.setText(String.format(Locale.CANADA, "%06d", dto.InventoryRowModel.ProductModelId));
        productCategory.setText(dto.CategoryRowModel.CategoryName);
        productSize.setText(String.format(Locale.CANADA, "%d", dto.CategoryRowModel.StorageSize));
        warehouseName.setText(dto.WarehouseRowModel.Address);
        arrivalDate.setText(dto.TrackingRowModel.ArrivalDate.toString());
    }
}
