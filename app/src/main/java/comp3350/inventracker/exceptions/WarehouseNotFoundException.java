package comp3350.inventracker.exceptions;

import java.util.Locale;

public class WarehouseNotFoundException
    extends Exception {
    public WarehouseNotFoundException(int warehouseId) {
        super(String.format(Locale.CANADA, "Warehouse with WarehouseId=%d not found.", warehouseId));
    }
}

