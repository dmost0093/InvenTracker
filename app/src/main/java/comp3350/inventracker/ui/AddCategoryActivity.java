package comp3350.inventracker.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.exceptions.CategoryAlreadyExistException;
import comp3350.inventracker.logic.ICategoryEditManager;

public class AddCategoryActivity
    extends Activity {
    
    public static final String ENTER_A_CATEGORY_NAME = "Enter a Category Name";
    public static final String ENTER_A_STORAGE_SIZE = "Enter a Storage Size";
    private TextView errorTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
    
        EditText categoryName    = findViewById(R.id.editCategoryName);
        EditText categoryStorage = findViewById(R.id.editStorageSpace);
        errorTextView = findViewById(R.id.errorMessage);
    
        Button checkButton = findViewById(R.id.submitButton);
        
        checkButton.setOnClickListener(v -> {
            String name = categoryName.getText().toString();
    
            if (name.isEmpty()) {
                errorTextView.setText(ENTER_A_CATEGORY_NAME);
                categoryName.requestFocus();
                return;
            }
            
            int storageSize;
            
            try {
                storageSize = Integer.parseInt(categoryStorage.getText().toString());
            }
            catch (NumberFormatException e) {
                errorTextView.setText(ENTER_A_STORAGE_SIZE);
                categoryStorage.requestFocus();
                return;
            }
    
            try {
                Services.get(ICategoryEditManager.class)
                        .AddCategory(name, storageSize);
            }
            catch (CategoryAlreadyExistException e) {
                errorTextView.setText(e.getLocalizedMessage());
                categoryName.requestFocus();
                return;
            }
    
            startActivity(new Intent(this, ViewCategoriesActivity.class));
        });
    }
}
