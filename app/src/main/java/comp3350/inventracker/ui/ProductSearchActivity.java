package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.ProductModelRowModel;
import comp3350.inventracker.logic.impl.ProductSearchManager;

public class ProductSearchActivity
    extends Activity {
    
    public static final String PRODUCT_DNE_MSG = "Product Model Id doesn't exist.";
    public static final String NO_INPUT_MSG = "Enter a Product Model Id.";
    private EditText modelIdInput;
    private TextView errorTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
    
        modelIdInput  = findViewById(R.id.modelIdInput);
        errorTextView = findViewById(R.id.errorMessage);
    }
    
    public void buttonSearch(View v) {
        String modelIdString;
        int    productModelId;
        
        try {
            modelIdString = modelIdInput.getText().toString();
            productModelId = Integer.parseInt(modelIdString);
        }
        catch (NumberFormatException e) {
            errorTextView.setText(NO_INPUT_MSG);
            return;
        }
    
        ProductModelRowModel productModel = Services.get(ProductSearchManager.class)
                                                    .GetProductModel(productModelId);
    
        if (productModel == null) {
            errorTextView.setText(PRODUCT_DNE_MSG);
        }
        else {
            Intent intent = new Intent(this, ProductModelItemsActivity.class);
            intent.putExtra("productModel", productModel);
            startActivity(intent);
        }
    }
}
