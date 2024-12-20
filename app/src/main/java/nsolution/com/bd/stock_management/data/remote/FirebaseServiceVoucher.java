package nsolution.com.bd.stock_management.data.remote;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nsolution.com.bd.stock_management.data.local.Voucher;
import nsolution.com.bd.stock_management.repository.VoucherRepository;

public class FirebaseServiceVoucher {
    private final DatabaseReference databaseReference;

    public FirebaseServiceVoucher() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("vouchers"); // Root node for vouchers
    }

    public void addVoucherToFirebase(Voucher voucher, VoucherRepository.Callback<Void> callback) {
        String voucherId = databaseReference.push().getKey();
        voucher.setvId(voucherId);
        databaseReference.child(voucherId)
                .setValue(voucher)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(callback::onFailure);
    }

    public void fetchVouchersFromFirebase(VoucherRepository.Callback<List<Voucher>> callback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Voucher> voucherList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Voucher voucher = snapshot.getValue(Voucher.class);
                    if (voucher != null) {
                        voucher.setvId(snapshot.getKey());
                        voucherList.add(voucher);
                    }
                }
                callback.onSuccess(voucherList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });
    }
}

