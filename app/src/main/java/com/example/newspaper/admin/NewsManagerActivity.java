package com.example.newspaper.admin;

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

import com.example.newspaper.model.NewsModel;
import com.example.newspaper.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class NewsManagerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NewsManagerAdapter mainAdapter;
    FloatingActionButton floatingActionButton;
    FloatingActionButton floatingUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsmanager);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<NewsModel> options =
                new FirebaseRecyclerOptions.Builder<NewsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("NewspaperInfo"), NewsModel.class)
                        .build();

        mainAdapter= new NewsManagerAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mainAdapter.stopListening();
    }
    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
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

        mainAdapter = new NewsManagerAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}
