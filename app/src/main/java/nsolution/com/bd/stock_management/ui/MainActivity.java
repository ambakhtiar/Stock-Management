package nsolution.com.bd.stock_management.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import nsolution.com.bd.stock_management.ui.Voucher.HistoryActivity;
import nsolution.com.bd.stock_management.ui.Product.ProductManagerActivity;
import nsolution.com.bd.stock_management.ui.Product.ProductsListActivity;
import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.ui.Voucher.VoucherMakerActivity;

public class MainActivity extends AppCompatActivity {

    Button btnMakeVoucher, btnSellingHistory, btnManageProducts, btnProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMakeVoucher = findViewById(R.id.btnMakeVoucher);
        btnSellingHistory = findViewById(R.id.btnSellingHistory);
        btnManageProducts = findViewById(R.id.btnManageProducts);
        btnProductList = findViewById(R.id.btnProductList);

        btnMakeVoucher.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, VoucherMakerActivity.class));
        });

        btnSellingHistory.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
        });

        btnManageProducts.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ProductManagerActivity.class));
        });

        btnProductList.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ProductsListActivity.class));
        });
    }
}
