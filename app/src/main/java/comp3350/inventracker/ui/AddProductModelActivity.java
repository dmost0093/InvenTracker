package comp3350.inventracker.ui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.CategoryRowModel;
import comp3350.inventracker.exceptions.CategoryNotFoundException;
import comp3350.inventracker.logic.ICategoriesManager;
import comp3350.inventracker.logic.IProductModelManager;

public class AddProductModelActivity
    extends Activity{
    
    public static final String CATEGORY_DOES_NOT_EXIST = "category does not exist";
    public static final String ENTER_PRODUCT_NAME = "Enter Product Name";
    public static final String ENTER_CATEGORY = "Enter Category";
    public static final String ENTER_COST = "Enter Cost";
    public static final String ENTER_MSRP = "Enter MSRP";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_model_info);
        
        EditText productName = findViewById(R.id.editProductName);
        EditText productCategoryId = findViewById(R.id.editCategoryId);
        EditText productCost = findViewById(R.id.editCost);
        EditText productMsrp = findViewById(R.id.editMsrp);
        
        Button checkButton = findViewById(R.id.submitButton);
    
        TextView errorTextView = findViewById(R.id.errorMessage);
    
    
        checkButton.setOnClickListener(v ->{
    
            int categoryId;
            int cost;
            int msrp;
            String product_name = productName.getText().toString();
            
            if (product_name.isEmpty()) {
                errorTextView.setText(ENTER_PRODUCT_NAME);
                productName.requestFocus();
                return;
            }
            
            try {
                categoryId = Integer.parseInt(productCategoryId.getText().toString());
            }
            catch (NumberFormatException e) {
                errorTextView.setText(ENTER_CATEGORY);
                productCategoryId.requestFocus();
                return;
            }
    
            try {
                cost = Integer.parseInt(productCost.getText().toString());
            }
            catch (NumberFormatException e) {
                errorTextView.setText(ENTER_COST);
                productCost.requestFocus();
                return;
            }
    
            try {
                msrp = Integer.parseInt(productMsrp.getText().toString());
            }
            catch (NumberFormatException e) {
                errorTextView.setText(ENTER_MSRP);
                productMsrp.requestFocus();
                return;
            }
    
            try {
                CategoryRowModel categoryRowModel = Services.get(ICategoriesManager.class)
                                                            .GetCategoryById(categoryId);
    
                if (categoryRowModel == null) {
                    errorTextView.setText(CATEGORY_DOES_NOT_EXIST);
                    return;
                }
    
                Services.get(IProductModelManager.class)
                        .AddProductModel(
                            product_name,
                            categoryId,
                            BigDecimal.valueOf(cost),
                            BigDecimal.valueOf(msrp)
                        );
                
                Intent intent = new Intent(AddProductModelActivity.this, SearchResultsActivity.class);
                AddProductModelActivity.this.startActivity(intent);
            }
            catch (CategoryNotFoundException e) {
                errorTextView.setText(CATEGORY_DOES_NOT_EXIST);
            }
        });
    }
}
