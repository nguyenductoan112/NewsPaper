package com.example.newspaper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class NewsActivity extends AppCompatActivity {

    private void reset(){
        Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
        startActivity(intent);
        finish();
    }
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    FloatingActionButton btnSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<NewsModel> options =
                new FirebaseRecyclerOptions.Builder<NewsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("NewspaperInfo"), NewsModel.class)
                        .build();
        newsAdapter = new NewsAdapter(options);
        recyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsModel model) {
                // Chuyển sang trang mới để đọc bài báo
                Intent intent = new Intent(NewsActivity.this, DetailsNewsActivity.class);
                intent.putExtra("title", model.getTitle());
                intent.putExtra("pubDate", model.getPubDate());
                intent.putExtra("imageUrl", model.getImage_url());
                intent.putExtra("author", model.getAuthor());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("content", model.getContent());
                startActivity(intent);
            }
        });

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenti = new Intent(NewsActivity.this, SettingActivity.class);
                startActivity(intenti);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        newsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        newsAdapter.stopListening();
    }

    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        //doc lai ham nay
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str) {
        FirebaseRecyclerOptions<NewsModel> options =
                new FirebaseRecyclerOptions.Builder<NewsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("NewspaperInfo").orderByChild("title").startAt(str).endAt(str + "~"), NewsModel.class)
                        .build();
        newsAdapter = new NewsAdapter(options);
        newsAdapter.startListening();
        recyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewsModel model) {
                // Chuyển sang trang mới để đọc bài báo
                Intent intent = new Intent(NewsActivity.this, DetailsNewsActivity.class);
                intent.putExtra("title", model.getTitle());
                intent.putExtra("pubDate", model.getPubDate());
                intent.putExtra("imageUrl", model.getImage_url());
                intent.putExtra("author", model.getAuthor());
                intent.putExtra("description", model.getDescription());
                intent.putExtra("content", model.getContent());
                startActivity(intent);
            }
        });
    }
}