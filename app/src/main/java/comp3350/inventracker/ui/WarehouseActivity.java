package comp3350.inventracker.ui;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

import java.util.List;
import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.app.Services;
import comp3350.inventracker.dtos.WarehouseDto;
import comp3350.inventracker.logic.IWarehouseAnalyticsManager;

public class WarehouseActivity extends Activity {
    
    private List<WarehouseDto> warehouseDtoList;
    private ArrayAdapter<WarehouseDto> warehouseDtoArrayAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_warehouse_list);
        
        try {
            IWarehouseAnalyticsManager warehouseAnalystManager = Services.get(IWarehouseAnalyticsManager.class);
            warehouseDtoList = warehouseAnalystManager.GetAllSpaceInUse();
            
            setupListView();
            
            Button buttonAdd = findViewById(R.id.buttonAdd);
            buttonAdd.setOnClickListener(v -> startActivity(new Intent(WarehouseActivity.this, AddWarehouseActivity.class)));
            
        } catch (final Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateWarehouseList();
    }
    
    private void updateWarehouseList() {
        try {
            IWarehouseAnalyticsManager warehouseAnalystManager = Services.get(IWarehouseAnalyticsManager.class);
            warehouseDtoList = warehouseAnalystManager.GetAllSpaceInUse();
            warehouseDtoArrayAdapter.clear();
            warehouseDtoArrayAdapter.addAll(warehouseDtoList);
            warehouseDtoArrayAdapter.notifyDataSetChanged();
        } catch (final Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
    
    private void setupListView() {
        warehouseDtoArrayAdapter = new ArrayAdapter<WarehouseDto>(
            this,
            R.layout.listitem_warehouse,
            R.id.WarehouseLocation,
            warehouseDtoList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
    
                WarehouseDto warehouseDto = warehouseDtoList.get(position);
    
                ProgressBar storageProgressBar  = view.findViewById(R.id.warehousePercentProgressBar);
                TextView    warehouseLocation   = view.findViewById(R.id.WarehouseLocation);
                TextView    warehouseCapacity   = view.findViewById(R.id.WarehouseCapacity);
                TextView    warehouseSpaceInUse = view.findViewById(R.id.WarehouseSpaceInUse);
                TextView    warehouseGetPercent = view.findViewById(R.id.WarehousePercentFull);
    
                double percent = warehouseDto.getPercentInUse() * 100;
    
                warehouseLocation.setText(warehouseDto.Warehouse.Address);
                warehouseCapacity.setText(String.format(Locale.CANADA, "%d", warehouseDto.Warehouse.Capacity));
                warehouseSpaceInUse.setText(String.format(Locale.CANADA, "%d", warehouseDto.SpaceInUse));
                warehouseGetPercent.setText(String.format(Locale.CANADA, "%.2f%%", percent));
    
                storageProgressBar.setIndeterminate(false);
                storageProgressBar.setMax(100);
                storageProgressBar.setProgress((int)percent);
    
                return view;
            }
        };
    
        ListView listView = findViewById(R.id.liveViewWarehouse);
        listView.setAdapter(warehouseDtoArrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> startWarehouseInfoActivity(position));
        
        AdapterView.OnItemSelectedListener onItemSelectedListener = listView.getOnItemSelectedListener();
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(null, null, 0, 0);
        }
    }
    
    private void startWarehouseInfoActivity(int position) {
        Intent intent = new Intent(WarehouseActivity.this, WarehouseInfoActivity.class);
        WarehouseDto selectedWarehouse = warehouseDtoList.get(position);
        intent.putExtra("selectedWarehouse", selectedWarehouse);
        startActivity(intent);
    }
}
