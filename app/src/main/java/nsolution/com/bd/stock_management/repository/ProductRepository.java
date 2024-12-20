package nsolution.com.bd.stock_management.repository;

import android.content.Context;

import nsolution.com.bd.stock_management.data.local.Product;
import nsolution.com.bd.stock_management.data.local.ProductDatabase;
import nsolution.com.bd.stock_management.data.remote.FirebaseService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private ProductDatabase productDatabase;
    private FirebaseService firebaseService;
    private final ExecutorService executorService;
    public ProductRepository(Context context) {
        productDatabase = ProductDatabase.getInstance(context);
        firebaseService = new FirebaseService();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void addProduct(Product product, boolean isOnline, Callback<Void> callback) {
        if (isOnline) {
            firebaseService.addProductToFirebase(product, callback);
        } else {
            executorService.execute(() -> {
                try {
                    productDatabase.productDao().insertProduct(product);
                    callback.onSuccess(null);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            });
        }
    }


    public void getProducts(boolean isOnline, Callback<List<Product>> callback) {
        if (isOnline) {
            firebaseService.fetchProductsFromFirebase(new Callback<List<Product>>() {
                @Override
                public void onSuccess(List<Product> result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onFailure(Exception e) {
                    callback.onFailure(e);
                }
            });
        } else {
            executorService.execute(() -> {
                try {
                    List<Product> products = productDatabase.productDao().getAllProducts();
                    callback.onSuccess(products);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            });
        }
    }

    public void updateProduct(int localId, int stock, double price, boolean isOnline, String firebaseId, Callback<Void> callback) {
        if (isOnline) {
            firebaseService.updateProductInFirebase(firebaseId, stock, price, callback);
        } else {
            executorService.execute(() -> {
                try {
                    productDatabase.productDao().updateProduct(localId, stock, price);
                    callback.onSuccess(null);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            });
        }
    }

    public void searchProducts(String query, boolean isOnline, Callback<List<Product>> callback) {
        if (isOnline) {
            firebaseService.searchProductsInFirebase(query, new Callback<List<Product>>() {
                @Override
                public void onSuccess(List<Product> result) {
                    callback.onSuccess(result);
                }

                @Override
                public void onFailure(Exception e) {
                    callback.onFailure(e);
                }
            });
        } else {
            executorService.execute(() -> {
                try {
                    List<Product> results = productDatabase.productDao().searchProducts("%" + query + "%");
                    callback.onSuccess(results);
                } catch (Exception e) {
                    callback.onFailure(e);
                }
            });
        }
    }


    public interface Callback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }
}
