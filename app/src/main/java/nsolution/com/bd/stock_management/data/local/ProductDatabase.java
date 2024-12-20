package nsolution.com.bd.stock_management.data.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

@Database(entities = {Product.class, Voucher.class}, version = 2)
@TypeConverters({ProductListConverter.class})
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract VoucherDao voucherDao();
    private static ProductDatabase INSTANCE;

    public static synchronized ProductDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductDatabase.class, "product_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}

