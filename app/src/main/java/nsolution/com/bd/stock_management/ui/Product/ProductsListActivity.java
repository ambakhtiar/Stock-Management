package nsolution.com.bd.stock_management.ui.Product;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Product;
import nsolution.com.bd.stock_management.repository.ProductRepository;
import nsolution.com.bd.stock_management.utils.NetworkUtils;

public class ProductsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductListAdapter productListAdapter;
    //DatabaseHelper databaseHelper;
    private ProductRepository productRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        //databaseHelper = new DatabaseHelper(this);
        productRepository = new ProductRepository(this);

        // Fetch product list from database
        //ArrayList<Product> productList = databaseHelper.getAllProducts();


        // Initialize the adapter with an empty list
        productListAdapter = new ProductListAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(productListAdapter);

        fetchProducts();
    }

    private void fetchProducts() {
        boolean isOnline = NetworkUtils.isOnline(this);

        productRepository.getProducts(isOnline, new ProductRepository.Callback<>() {
            @Override
            public void onSuccess(List<Product> result) {
                runOnUiThread(() -> {
                    productListAdapter.updateProducts(result);
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(ProductsListActivity.this, "Error fetching products: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

        });
    }
}
