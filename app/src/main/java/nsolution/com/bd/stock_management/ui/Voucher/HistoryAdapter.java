package nsolution.com.bd.stock_management.ui.Voucher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Voucher;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private ArrayList<Voucher> voucherList;

    public HistoryAdapter(ArrayList<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);
        holder.tvProductName.setText("Product: " + voucher.voucherName);
        holder.tvQuantity.setText("Quantity: " + voucher.quantity);
        holder.tvTotalPrice.setText("Total Price: $" + voucher.totalPrice);
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvQuantity, tvTotalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}
