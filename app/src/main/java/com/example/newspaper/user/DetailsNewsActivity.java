package com.example.newspaper.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsNewsActivity extends AppCompatActivity {
    TextView authorText, titleText, pubDateText, descriptionText, contentText;
    ImageView img;
    FloatingActionButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        // Liên kết các view từ layout
        authorText = findViewById(R.id.authorText);
        titleText = findViewById(R.id.titleText);
        pubDateText = findViewById(R.id.pubDateText);
        descriptionText = findViewById(R.id.descriptionText);
        contentText = findViewById(R.id.contentText);
        img = findViewById(R.id.img);
        btnBack = findViewById(R.id.btnBack);

        // Lấy dữ liệu từ Intent và hiển thị lên các view
        Intent intent = getIntent();
        if (intent != null) {
            String author = intent.getStringExtra("author");
            String title = intent.getStringExtra("title");
            String pubDate = intent.getStringExtra("pubDate");
            String description = intent.getStringExtra("description");
            String content = intent.getStringExtra("content");
            String imageUrl = intent.getStringExtra("imageUrl");

            authorText.setText(author);
            titleText.setText(title);
            pubDateText.setText(pubDate);
            descriptionText.setText(description);
            contentText.setText(content);

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(img);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NewsActivity.class));
            }
        });
    }
}

