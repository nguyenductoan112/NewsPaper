package com.example.newspaper.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.R;

public class AdminActivity extends AppCompatActivity {
    private void reset(){
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(intent);
        finish();
    }
    private Button newsManager, userManager, ThemeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        newsManager = findViewById(R.id.admin_newsManager);
        userManager = findViewById(R.id.admin_userManager);
        ThemeManager = findViewById(R.id.ThemeManager);

        newsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, NewsManagerActivity.class));
            }
        });

        userManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, UserManagerActivity.class));
            }
        });

        ThemeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AdminSetUp.class));
            }
        });
    }
}