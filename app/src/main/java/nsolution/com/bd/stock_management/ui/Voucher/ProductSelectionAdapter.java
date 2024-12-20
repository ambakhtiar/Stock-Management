package nsolution.com.bd.stock_management.ui.Voucher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Product;

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.ProductViewHolder> {

    private final List<Product> products;
    private final ArrayList<Product> selectedProducts = new ArrayList<>();

    public ProductSelectionAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_selection, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productStock.setText("Stock: " + product.getStock());
        holder.productPrice.setText("$" + product.getPrice());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedProducts.contains(product));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                product.setQnt(Integer.parseInt(String.valueOf(holder.productQnt.getText())));
                selectedProducts.add(product);
            } else {
                selectedProducts.remove(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public ArrayList<Product> getSelectedProducts() {
        return selectedProducts;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productStock, productPrice;
        EditText productQnt;
        CheckBox checkBox;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textProductName);
            productStock = itemView.findViewById(R.id.textProductStock);
            productPrice= itemView.findViewById(R.id.textProductPrice);
            productQnt= itemView.findViewById(R.id.qntSelect);
            checkBox = itemView.findViewById(R.id.checkboxSelectProduct);
        }
    }
}
