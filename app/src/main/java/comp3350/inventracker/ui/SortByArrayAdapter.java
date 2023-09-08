package comp3350.inventracker.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import comp3350.inventracker.dtos.SortingOption;

public class SortByArrayAdapter
    extends ArrayAdapter<SortingOption> {
    
    public SortByArrayAdapter(@NonNull Context context, SortingOption[] objects) {
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
    
        SortingOption option = getItem(position);
        
        if (option != null) {
            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(option.name);
        }
        
        return view;
    }
}
