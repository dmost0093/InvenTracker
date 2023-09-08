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

import comp3350.inventracker.dtos.WarehouseRowModel;

public class WarehouseArrayAdapter
    extends ArrayAdapter<WarehouseRowModel> {
    
    public WarehouseArrayAdapter(@NonNull Context context, List<WarehouseRowModel> objects) {
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
        
        WarehouseRowModel warehouse = getItem(position);
        if (warehouse != null) {
            TextView textView = view.findViewById(android.R.id.text1);
            
            textView.setText(String.format(
                Locale.CANADA,
                "%02d (%s)",
                warehouse.WarehouseID,
                warehouse.Address
            ));
        }
        
        return view;
    }
}
