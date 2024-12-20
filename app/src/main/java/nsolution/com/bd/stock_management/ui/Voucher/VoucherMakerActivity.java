package nsolution.com.bd.stock_management.ui.Voucher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Product;
import nsolution.com.bd.stock_management.data.local.Voucher;
import nsolution.com.bd.stock_management.repository.VoucherRepository;

public class VoucherMakerActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_PRODUCTS = 1;

    private EditText voucherNameInput;
    private TextView selectedProductsText;
    private Button selectProductsButton, addVoucherButton;
    private VoucherRepository voucherRepository;
    private ArrayList<Product> selectedProducts = new ArrayList<>();
    double totalPrice = 0.0;
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_maker);


        voucherNameInput = findViewById(R.id.inputVoucherName);
        selectedProductsText = findViewById(R.id.textSelectedProducts);
        selectProductsButton = findViewById(R.id.buttonSelectProducts);
        addVoucherButton = findViewById(R.id.buttonAddVoucher);

        voucherRepository = new VoucherRepository(this);

        selectProductsButton.setOnClickListener(v -> openProductSelection());
        addVoucherButton.setOnClickListener(v -> addVoucher());
    }
    private void openProductSelection() {
        Intent intent = new Intent(this, SelectProductsActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_PRODUCTS);
    }

    private void addVoucher() {
        String voucherName = voucherNameInput.getText().toString().trim();

        if (voucherName.isEmpty() || selectedProducts.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields and select products", Toast.LENGTH_SHORT).show();
            return;
        }

        Voucher voucher = new Voucher(voucherName, selectedProducts, quantity, totalPrice);

        voucherRepository.addVoucher(voucher, true, new VoucherRepository.Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(VoucherMakerActivity.this, "Voucher added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(VoucherMakerActivity.this, "Error adding voucher: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PRODUCTS && resultCode == RESULT_OK && data != null) {
            selectedProducts = data.getParcelableArrayListExtra("selectedProducts");
            updateSelectedProductsText();
        }
    }

    private void updateSelectedProductsText() {
        StringBuilder productsText = new StringBuilder();
        for (Product product : selectedProducts) {
            productsText.append(product.getName()).append(", ");
            totalPrice += product.getPrice()*product.getQnt();
            quantity += product.getQnt();
        }
        selectedProductsText.setText(productsText.toString());
    }
}
