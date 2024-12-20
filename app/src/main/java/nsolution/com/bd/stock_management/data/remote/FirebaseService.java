package nsolution.com.bd.stock_management.data.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nsolution.com.bd.stock_management.data.local.Product;
import nsolution.com.bd.stock_management.repository.ProductRepository;

public class FirebaseService {
    private final DatabaseReference databaseReference;

    public FirebaseService() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("products"); // Root node for products
    }

    // Add Product
    public void addProductToFirebase(Product product, ProductRepository.Callback<Void> callback) {
        String productId = databaseReference.push().getKey();
        product.setPId(productId);
        databaseReference.child(productId)
                .setValue(product)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    // Fetch All Products
    public void fetchProductsFromFirebase(ProductRepository.Callback<List<Product>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        product.setPId(snapshot.getKey());
                        productList.add(product);
                    }
                }
                callback.onSuccess(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }

    // Update Product
    public void updateProductInFirebase(String firebaseId, int stock, double price, ProductRepository.Callback<Void> callback) {
        if (firebaseId == null || firebaseId.isEmpty()) {
            callback.onFailure(new Exception("Invalid product ID"));
            return;
        }

        databaseReference.child(firebaseId)
                .updateChildren(new HashMap<String, Object>() {{
                    put("stock", stock);
                    put("price", price);
                }})
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    // Search Products
    public void searchProductsInFirebase(String query, ProductRepository.Callback<List<Product>> callback) {
        databaseReference.orderByChild("name")
                .startAt(query)
                .endAt(query + "\uf8ff") // Special character for prefix matching
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Product> results = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Product product = snapshot.getValue(Product.class);
                            if (product != null) {
                                product.setPId(snapshot.getKey());
                                results.add(product);
                            }
                        }
                        callback.onSuccess(results);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure(databaseError.toException());
                    }
                });
    }
}
