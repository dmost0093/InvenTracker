package comp3350.inventracker.dtos;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class CategoryRowModel
    implements Comparable<CategoryRowModel>,
               Serializable {
    
    public final int    CategoryId;
    public final String CategoryName;
    public final int    StorageSize;
    
    public CategoryRowModel(int categoryId) {
        CategoryId   = categoryId;
        CategoryName = "";
        StorageSize  = -1;
    }
    
    public CategoryRowModel(int categoryId, String categoryName, int storageSize) {
        CategoryId   = categoryId;
        CategoryName = categoryName;
        StorageSize  = storageSize;
    }
    
    /**
     * Compares Categories by CategoryId
     */
    @Override
    public int compareTo(@NonNull CategoryRowModel other) {
        return CategoryId - other.CategoryId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryRowModel that = (CategoryRowModel) o;
        return CategoryId == that.CategoryId;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(CategoryId);
    }
}
