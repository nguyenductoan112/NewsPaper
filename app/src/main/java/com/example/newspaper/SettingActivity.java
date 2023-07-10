package com.example.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private void reset() {
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        finish();
    }

    Button btnchange;
    Button btnquanly;
    Button btnlienhe;
    Button btndksd;
    Button btntc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnchange = findViewById(R.id.btnchange);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ThemeActivity.class));
            }
        });
        btnquanly = findViewById(R.id.btnquanly);
        btnquanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, UserActivity.class));
            }
        });
        btnlienhe = findViewById(R.id.btnlienhe);
        btnlienhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ContactActivity.class));
            }
        });
        btndksd = findViewById(R.id.btndksd);
        btndksd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, TermActivity.class));
            }
        });

        btntc = findViewById(R.id.btntc);
        btntc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, NewsActivity.class));
            }
        });
    }

}
