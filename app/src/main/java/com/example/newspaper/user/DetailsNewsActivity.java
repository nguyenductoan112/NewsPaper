package com.example.newspaper.user;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newspaper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class DetailsNewsActivity extends AppCompatActivity {
    TextView authorText, titleText, pubDateText, descriptionText, contentText;
    ImageView img;
    FloatingActionButton btnBack, btnSpeak;
    private boolean isMuted = true;
    TextToSpeech textToSpeech;

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
        btnSpeak = findViewById(R.id.btnSpeak);

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

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("vi"));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(DetailsNewsActivity.this, "Ngôn ngữ không được hỗ trợ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailsNewsActivity.this, "Khởi tạo TextToSpeech thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textToSpeech != null) {
                    textToSpeech.stop();
                    textToSpeech.shutdown();
                }
                startActivity(new Intent(getApplicationContext(), NewsActivity.class));
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMuted) {
                    // Đọc nội dung của các TextView
                    String title = titleText.getText().toString();
                    String pubDate = pubDateText.getText().toString();
                    String description = descriptionText.getText().toString();
                    String content = contentText.getText().toString();

                    String fullContent = title + ". " + pubDate + ". " + description + ". " + content;

                    textToSpeech.speak(fullContent, TextToSpeech.QUEUE_FLUSH, null);

                    btnSpeak.setImageResource(R.drawable.volume_up);
                } else {
                    textToSpeech.stop();
                    btnSpeak.setImageResource(R.drawable.volume_mute);
                }
                isMuted = !isMuted;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    public void onBackPressed() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onBackPressed();
    }
}
