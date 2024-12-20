package nsolution.com.bd.stock_management.ui.Product;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Product;
import nsolution.com.bd.stock_management.repository.ProductRepository;
import nsolution.com.bd.stock_management.utils.NetworkUtils;

public class ProductManagerActivity extends AppCompatActivity {

    EditText etProductName, etPrice, etStock;
    Button btnAddProduct;
    //DatabaseHelper databaseHelper;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manager);

        etProductName = findViewById(R.id.etProductName);
        etPrice = findViewById(R.id.etPrice);
        etStock = findViewById(R.id.etStock);
        btnAddProduct = findViewById(R.id.btnAddProduct);
//        databaseHelper = new DatabaseHelper(this);
        productRepository = new ProductRepository(this);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etProductName.getText().toString();
                double price = Double.parseDouble(etPrice.getText().toString());
                int stock = Integer.parseInt(etStock.getText().toString());

                if (!name.isEmpty()) {
                    Product product = new Product();
                    product.setName(name);
                    product.setStock(stock);
                    product.setPrice(price);

                    boolean isOnline = NetworkUtils.isOnline(ProductManagerActivity.this);
                    productRepository.addProduct(product, isOnline, new ProductRepository.Callback<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            Toast.makeText(ProductManagerActivity.this, "Product added!", Toast.LENGTH_SHORT).show();
                            etProductName.setText("");
                            etPrice.setText("");
                            etStock.setText("");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(ProductManagerActivity.this, "Error!:" + e, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}
