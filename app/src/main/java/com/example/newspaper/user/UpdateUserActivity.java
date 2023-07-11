package com.example.newspaper.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.R;

public class UpdateUserActivity extends AppCompatActivity {

    Button btnuser;
//    Button btnsave;
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference reference;
//    DocumentReference documentReference;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//    FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
//    String currentuid = User.getUid();
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        btnuser = findViewById(R.id.btnuser);
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}