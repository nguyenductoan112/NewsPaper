package com.example.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserActivity extends AppCompatActivity {
    Button btnback3;
    Button btnup;

    private void reset() {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(intent);
        finish();
    }
//
//    UploadTask uploadTask;
//    FirebaseStorage firebaseStorage;
//    StorageReference storageReference;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
//    String currentuid = User.getUid();

    TextView fullnameht, emailht, phoneht;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
    String currentUid = User.getUid();
    DocumentReference documentReference = db.collection("User").document(currentUid);


//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.getResult().exists()){
//                            String name = task.getResult().getString("name");
//                            String email = task.getResult().getString("email");
//                            String phone = task.getResult().getString("phoneNumber");
//
//                            fullnameht.setText(name);
//                            emailht.setText(email);
//                            phoneht.setText(phone);
//                       }
//                    }
//                });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnup = findViewById(R.id.btnup);
        btnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, UpdateUserActivity.class));
            }
        });
        btnback3 = findViewById(R.id.btnback3);
        btnback3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thoat activity
                finish();
            }
        });

//        documentReference.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            // Lấy dữ liệu từ tài liệu Firestore
//
//                            String name = documentSnapshot.getString("name");
//                            String email = documentSnapshot.getString("email");
//                            String phone = documentSnapshot.getString("phoneNumber");
//
//                            //setup giá trị
//                            fullnameht.setText(name);
//                            emailht.setText(email);
//                            phoneht.setText(phone);
//
//                            // Hiển thị thông tin lên giao diện người dùng
//                            TextView fullnameht = findViewById(R.id.fullnameht);
//                            TextView emailht = findViewById(R.id.emailht);
//                            TextView phoneht = findViewById(R.id.phoneht);
//
//                        } else {
//                            Log.d("TAG", "Tài liệu không tồn tại");
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("TAG", "Lỗi khi truy cập vào Firestore", e);
//                    }
//                });
//    }
//
//
//


////        DocumentReference documentReference = db.collection("User").document(currentuid);
//            documentReference.get()
//                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//                            if (documentSnapshot.exists()) {
//                                // Lấy dữ liệu từ tài liệu Firestore
//
//                                String name = documentSnapshot.getString("name");
//                                String email = documentSnapshot.getString("email");
//                                String phone = documentSnapshot.getString("phoneNumber");
//
//                                //setup giá trị
//                                fullnameht.setText(name);
//                                emailht.setText(email);
//                                phoneht.setText(phone);
//
//                                // Hiển thị thông tin lên giao diện người dùng
//                                TextView fullnameht = findViewById(R.id.fullnameht);
//                                TextView emailht = findViewById(R.id.emailht);
//                                TextView phoneht = findViewById(R.id.phoneht);
//
//                            } else {
//                                Log.d("TAG", "Tài liệu không tồn tại");
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d("TAG", "Lỗi khi truy cập vào Firestore", e);
//                        }
//                    });

    }
}