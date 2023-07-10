package com.example.newspaper;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    Button btncd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        btncd = findViewById(R.id.btncd);
        btncd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //thoat activity
                finish();
            }
        });
    }
}