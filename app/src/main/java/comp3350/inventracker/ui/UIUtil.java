package comp3350.inventracker.ui;

import android.widget.AdapterView;
import android.widget.Spinner;

public class UIUtil {
    public static Object getCurrentDropdownItem(Spinner dropdown) {
        int selectedPosition = dropdown.getSelectedItemPosition();
        return selectedPosition != AdapterView.INVALID_POSITION
            ? dropdown.getItemAtPosition(selectedPosition)
            : null;
    }
}
