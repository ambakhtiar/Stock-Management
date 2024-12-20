package nsolution.com.bd.stock_management.ui.Voucher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class SelectProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductSelectionAdapter adapter;
    private Button doneButton;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_products);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        doneButton = findViewById(R.id.buttonDone);
        productRepository = new ProductRepository(this);

        fetchProducts();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> selectedProducts = adapter.getSelectedProducts();
                if (selectedProducts.isEmpty()) {
                    Toast.makeText(SelectProductsActivity.this, "No products selected", Toast.LENGTH_SHORT).show();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putParcelableArrayListExtra("selectedProducts", selectedProducts);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    private void fetchProducts() {
        boolean isOnline = NetworkUtils.isOnline(this);

        productRepository.getProducts(isOnline, new ProductRepository.Callback<>() {
            @Override
            public void onSuccess(List<Product> result) {
                runOnUiThread(() -> {
                    setupRecyclerView(result);
                });
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(SelectProductsActivity.this, "Error fetching products: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

        });
    }

    private void setupRecyclerView(List<Product> products) {
        adapter = new ProductSelectionAdapter(products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
