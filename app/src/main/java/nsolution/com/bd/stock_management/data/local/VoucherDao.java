package nsolution.com.bd.stock_management.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VoucherDao {
    @Insert
    void insertVoucher(Voucher voucher);

    @Query("SELECT * FROM vouchers")
    List<Voucher> getAllVouchers();
}
