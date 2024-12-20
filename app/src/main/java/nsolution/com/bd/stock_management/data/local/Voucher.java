package nsolution.com.bd.stock_management.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "vouchers")
public class Voucher {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String vId, voucherName;

    @TypeConverters(ProductListConverter.class) // Custom converter for the product list
    public ArrayList<Product> products;

    public int quantity;

    public double totalPrice;

    public Voucher(String voucherName, ArrayList<Product> products, int quantity, double totalPrice) {
        this.voucherName = voucherName;
        this.products = products;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Default constructor for Firebase
    public Voucher() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

