package nsolution.com.bd.stock_management.repository;

import android.content.Context;
import java.util.List;
import nsolution.com.bd.stock_management.data.local.ProductDatabase;
import nsolution.com.bd.stock_management.data.local.Voucher;
import nsolution.com.bd.stock_management.data.remote.FirebaseServiceVoucher;

public class VoucherRepository {
    private final ProductDatabase productDatabase;
    private final FirebaseServiceVoucher firebaseService;

    public VoucherRepository(Context context) {
        productDatabase = ProductDatabase.getInstance(context);
        firebaseService = new FirebaseServiceVoucher();
    }

    // Add a Voucher
    public void addVoucher(Voucher voucher, boolean isOnline, Callback<Void> callback) {
        if (isOnline) {
            firebaseService.addVoucherToFirebase(voucher, new Callback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    callback.onSuccess(null);
                }

                @Override
                public void onFailure(Exception e) {
                    callback.onFailure(e);
                }
            });
        } else {
            new Thread(() -> {
                productDatabase.voucherDao().insertVoucher(voucher);
                callback.onSuccess(null);
            }).start();
        }
    }

    // Fetch all Vouchers
    public void fetchVouchers(boolean isOnline, Callback<List<Voucher>> callback) {
        if (isOnline) {
            firebaseService.fetchVouchersFromFirebase(new Callback<List<Voucher>>() {
                @Override
                public void onSuccess(List<Voucher> vouchers) {
                    callback.onSuccess(vouchers);
                }

                @Override
                public void onFailure(Exception e) {
                    callback.onFailure(e);
                }
            });
        } else {
            new Thread(() -> {
                List<Voucher> vouchers = productDatabase.voucherDao().getAllVouchers();
                callback.onSuccess(vouchers);
            }).start();
        }
    }

    // Define Callback Interface
    public interface Callback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }
}
