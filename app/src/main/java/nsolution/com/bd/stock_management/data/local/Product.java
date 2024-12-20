package nsolution.com.bd.stock_management.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String pId, name;
    private int stock,qnt;
    private double price;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPId() { return pId; }
    public void setPId(String pId) { this.pId = pId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getQnt() { return qnt; }
    public void setQnt(int qnt) { this.qnt = qnt; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Parcelable implementation
    protected Product(Parcel in) {
        id = in.readInt();
        pId = in.readString();
        name = in.readString();
        stock = in.readInt();
        qnt = in.readInt();
        price = in.readDouble();
    }
    public Product() {
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(pId);
        dest.writeString(name);
        dest.writeInt(stock);
        dest.writeInt(qnt);
        dest.writeDouble(price);
    }
}
