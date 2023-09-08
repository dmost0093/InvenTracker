# Database Tables with Sample Data

## UserLogins: **`UserLogins`**

| `Username` | `PasswordHash`                                                   | `PasswordSalt`           | `IsAdmin` |
|------------|------------------------------------------------------------------|--------------------------|-----------|
| admin      | 78cd1e83f2941234877b099286c90bb3fc15428649cfde7d96f1d4ef78839147 | 4jQch4jjpyzIY1aKU5jBtg== | TRUE      |
| rob.g      | 78cd1e83f2941234877b099286c90bb3fc15428649cfde7d96f1d4ef78839147 | 4jQch4jjpyzIY1aKU5jBtg== | FALSE     |

>>>
PasswordHash is a hash of the actual Password + PasswordSalt. In this way, if someone gains access to our database they don't automatically have access to the system with a password stored in clear text.

For the record, the password in each case is just **_`pass`_**
>>>

## Inventory: **`Inventory`**

| `ItemId` | `ProductModelId` |
|----------|------------------|
| 0        | 1                |
| 1        | 3                |

## Tracking: **`Tracking`**

|`ItemId`   |`Seq`  |`WarehouseId`  |`ArrivalDate`  |`DepartDate`   |
|-----------|-------|---------------|---------------|---------------|
| 0         | 0     | 0             | 2023/06/03    | 2023/06/05    |
| 1         | 0     | 0             | 2023/06/03    | 2023/06/05    |
| 1         | 1     | 1             | 2023/06/05    | NULL          |

## ProductModels: **`ProductModels`**

|`ProductModelId`   |`CategoryId`   |`ProductName`          |`Cost` |`MSRP` |
|-------------------|---------------|-----------------------|-------|-------|
| 1                 | 3             | White Small Toaster   | 30    | 50    |
| 2                 | 3             | Grey Toaster          | 10    | 30    |
| 3                 | 1             | SS Fridge             | 10    | 30    |

## Categories: **`Categories`**

|`CategoryId`   |`CategoryName`     |`Size` |
|---------------|-------------------|-------|
| 1             | Fridge            | 10    |
| 2             | Stove             | 10    |
| 3             | Toaster           | 10    |

## Warehouses: **`Warehouses`**

|`WarehouseId`  |`Location` |`Capacity` |
|---------------|-----------|-----------|
| 1             | Winnipeg  | 1 000     |
| 2             | Regina    | 10 000    |
