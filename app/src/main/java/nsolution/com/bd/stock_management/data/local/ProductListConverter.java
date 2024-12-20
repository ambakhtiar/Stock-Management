package nsolution.com.bd.stock_management.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ProductListConverter {
    @TypeConverter
    public static String fromProductList(ArrayList<Product> products) {
        Gson gson = new Gson();
        return gson.toJson(products);
    }

    @TypeConverter
    public static ArrayList<Product> toProductList(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
        return gson.fromJson(data, listType);
    }
}

