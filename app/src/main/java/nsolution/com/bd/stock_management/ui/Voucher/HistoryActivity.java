package nsolution.com.bd.stock_management.ui.Voucher;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nsolution.com.bd.stock_management.R;
import nsolution.com.bd.stock_management.data.local.Voucher;
import nsolution.com.bd.stock_management.repository.VoucherRepository;
import nsolution.com.bd.stock_management.utils.NetworkUtils;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    private VoucherRepository voucherRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);

        voucherRepository = new VoucherRepository(this);

        fetchVouchers();
    }

    private void fetchVouchers() {
        boolean isOnline = NetworkUtils.isOnline(this);
        voucherRepository.fetchVouchers(isOnline, new VoucherRepository.Callback<List<Voucher>>() {
            @Override
            public void onSuccess(List<Voucher> result) {
                setupRecyclerView(result);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void setupRecyclerView(List<Voucher> vouchers) {
        historyAdapter = new HistoryAdapter((ArrayList<Voucher>) vouchers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(historyAdapter);
    }
}
