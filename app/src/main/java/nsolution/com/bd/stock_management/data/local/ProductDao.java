package nsolution.com.bd.stock_management.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(nsolution.com.bd.stock_management.data.local.Product product);

    @Query("SELECT * FROM products")
    List<nsolution.com.bd.stock_management.data.local.Product> getAllProducts();

    @Query("DELETE FROM products")
    void deleteAllProducts();

    @Query("UPDATE products SET stock = :stock, price = :price WHERE id = :id")
    void updateProduct(int id, int stock, double price);

    @Query("SELECT * FROM products WHERE name LIKE :query")
    List<Product> searchProducts(String query);
}
