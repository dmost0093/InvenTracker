package comp3350.inventracker.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.dtos.InventoryItemDto;

public class InventoryDtoArrayAdapter
    extends ArrayAdapter<InventoryItemDto> {
    private final List<InventoryItemDto> list;
    
    public InventoryDtoArrayAdapter(Context context, List<InventoryItemDto> availableQuantities) {
        super(context, R.layout.listitem_product_model_inventory, R.id.itemNumberValue, availableQuantities);
        this.list = availableQuantities;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        
        InventoryItemDto inventoryItemDto = list.get(position);
    
        System.out.println(inventoryItemDto);
        
        TextView itemNumber    = view.findViewById(R.id.itemNumberValue);
        TextView warehouseName = view.findViewById(R.id.warehouseNameValue);
        TextView arrivalDate   = view.findViewById(R.id.arrivalDateValue);
        
        itemNumber.setText(String.format(Locale.CANADA, "%06d", inventoryItemDto.InventoryRow.InventoryId));
        warehouseName.setText(inventoryItemDto.WarehouseRow.Address);
        arrivalDate.setText(inventoryItemDto.LatestTracking.ArrivalDate.toString());
        
        return view;
    }
}
