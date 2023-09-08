package comp3350.inventracker.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

import comp3350.inventracker.dtos.CategoryRowModel;

public class CategoryArrayAdapter
    extends ArrayAdapter<CategoryRowModel> {
    
    public CategoryArrayAdapter(@NonNull Context context, List<CategoryRowModel> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
    }
    
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return this.getDropDownView(position, convertView, parent);
    }
    
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        
        CategoryRowModel category = getItem(position);
        if (category != null) {
            TextView textView = view.findViewById(android.R.id.text1);
            
            textView.setText(String.format(
                Locale.CANADA,
                "%02d (%s)",
                category.CategoryId,
                category.CategoryName
            ));
        }
        
        return view;
    }
}
