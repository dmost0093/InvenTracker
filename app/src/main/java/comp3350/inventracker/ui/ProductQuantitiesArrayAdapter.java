package comp3350.inventracker.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import comp3350.inventracker.R;
import comp3350.inventracker.dtos.ProductModelQuantityDto;

public class ProductQuantitiesArrayAdapter
    extends ArrayAdapter<ProductModelQuantityDto> {
    private final List<ProductModelQuantityDto> availableQuantities;
    
    public ProductQuantitiesArrayAdapter(Context context, List<ProductModelQuantityDto> availableQuantities) {
        super(context, R.layout.listitem_search_results, R.id.modelNumberValue, availableQuantities);
        this.availableQuantities = availableQuantities;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        
        ProductModelQuantityDto productModelQuantity = availableQuantities.get(position);
        
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        String       formattedMSRP     = currencyFormatter.format(productModelQuantity.ProductModel.MSRP);
        
        TextView modelNumber = view.findViewById(R.id.modelNumberValue);
        TextView modelName   = view.findViewById(R.id.modelNameValue);
        TextView quantity    = view.findViewById(R.id.modelQuantityValue);
        TextView category    = view.findViewById(R.id.modelCategoryName);
        TextView msrp        = view.findViewById(R.id.modelMSRP);
        
        modelName.setText(productModelQuantity.ProductModel.Name);
        category.setText(productModelQuantity.Category.CategoryName);
        modelNumber.setText(String.format(Locale.CANADA, "%04d", productModelQuantity.ProductModelId));
        quantity.setText(String.format(Locale.CANADA, "%d", productModelQuantity.Quantity));
        msrp.setText(formattedMSRP);
        
        return view;
    }
}
