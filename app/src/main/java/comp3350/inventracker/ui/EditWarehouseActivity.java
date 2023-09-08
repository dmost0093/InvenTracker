package comp3350.inventracker.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.logic.IWarehouseManager;
public class EditWarehouseActivity extends Activity{
    // User input
    private EditText warehouseAddressInput;
    private EditText warehouseCapacityInput;
    
    // Create feedback
    TextView createFeedback;
    
    private WarehouseDto selectedWarehouse;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_warehouse);
        
        selectedWarehouse = (WarehouseDto) getIntent().getSerializableExtra("selectedWarehouse");
        warehouseCapacityInput = findViewById(R.id.input_warehouseCapacity);
        
        createFeedback         = findViewById(R.id.errorMessage);
        
        warehouseCapacityInput.setText(String.valueOf(selectedWarehouse.Warehouse.Capacity));
        
        Button submitButton    = findViewById(R.id.submitButton);
        Button resetButton     = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v-> clearInput());
        submitButton.setOnClickListener(v->{
            try{
                int    warehouseCapacity = Integer.parseInt(warehouseCapacityInput.getText().toString());
                int    tempID            = selectedWarehouse.Warehouse.WarehouseID;
                Services.get(IWarehouseManager.class).UpdateWarehouse(tempID, warehouseCapacity);
                succeedEditing();
                onBackPressed();
            } catch (NumberFormatException exception){
                Log.d("EditWarehouseActivity", "Failed to parse capacity", exception);
                failedEditing();
            }
        });
        
    }
    void clearInput(){
        warehouseAddressInput.setText("");
        warehouseCapacityInput.setText("");
    }
    void succeedEditing() { feedbackException("Succeed updating warehouse with given information.", Color.GREEN); }
    
    void failedEditing() { feedbackException("Failed updating warehouse with given information.", Color.RED); }
    
    void feedbackException(String message, int color){
        createFeedback.setText(message);
        createFeedback.setVisibility(View.VISIBLE);
        createFeedback.setTextColor(color);
    }
}
