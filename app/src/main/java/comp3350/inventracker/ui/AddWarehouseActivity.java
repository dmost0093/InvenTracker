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
import comp3350.inventracker.logic.IWarehouseManager;

public class AddWarehouseActivity extends Activity{
    
    // User input
    private EditText warehouseAddressInput;
    private EditText warehouseCapacityInput;
    
    // create feedback
    TextView createFeedback;
  
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);
    
        warehouseAddressInput  = findViewById(R.id.input_warehouseAddress);
        warehouseCapacityInput = findViewById(R.id.input_warehouseCapacity);
        createFeedback         = findViewById(R.id.errorMessage);
    
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v->{ String capacityString = warehouseCapacityInput.getText().toString();
            if (capacityString.isEmpty()) {
                // Show an error message if the capacity input is empty
                failedAdding();
            } else {
                try {
                    String warehouseAddress  = warehouseAddressInput.getText().toString();
                    int    warehouseCapacity = Integer.parseInt(warehouseCapacityInput.getText().toString());
    
                    Services.get(IWarehouseManager.class).AddWarehouse(warehouseAddress, warehouseCapacity);
                    
                    succeedAdding();
                    onBackPressed();
                } catch (NumberFormatException exception) {
                    Log.d("AddWarehouseActivity", "Failed to parse capacity", exception);
                    failedAdding();
                } catch (Exception exception) {
                    Log.d("AddWarehouseActivity", "Failed to add warehouse", exception);
                    failedAdding();
                }
            }
        });
    }
    
    void succeedAdding(){
        feedbackException("Succeed adding new warehouse", Color.GREEN);
    }
    
    void failedAdding(){
        feedbackException("Failed adding new warehouse because of invalid input", Color.RED);
    }
    
    void feedbackException(String message, int color){
        createFeedback.setText(message);
        createFeedback.setVisibility(View.VISIBLE);
        createFeedback.setTextColor(color);
    }
}