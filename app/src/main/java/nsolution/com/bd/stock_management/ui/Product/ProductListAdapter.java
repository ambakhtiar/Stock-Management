package nsolution.com.bd.stock_management.ui.Product;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Product;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private ArrayList<Product> productList;

    public ProductListAdapter(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductId.setText("ID: " + product.getPId());
        holder.tvProductName.setText("Product: " + product.getName());
        holder.tvPrice.setText("Price: $" + product.getPrice());
        holder.tvStock.setText("Stock: " + product.getStock());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateProducts(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductId, tvProductName, tvPrice, tvStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductId = itemView.findViewById(R.id.tvProductId);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
        }
    }
}
