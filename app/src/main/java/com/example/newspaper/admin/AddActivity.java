package com.example.newspaper.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText title,author,category,pubDate, description, content,image_url;
    Button btnAdd, btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager .LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_add_news);

        title=findViewById(R.id.txttitleadd);
        author=findViewById(R.id.txtauthoradd);
        category=findViewById(R.id.txtcategoryadd);
        pubDate=findViewById(R.id.txtpubDateadd);
        description=findViewById(R.id.txtdescriptionadd);
        content=findViewById(R.id.txtcontentadd);
        image_url=findViewById(R.id.txtimage_urladd);

        btnAdd=findViewById(R.id.btnAdd);
        btnBack=findViewById(R.id.btnBack);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                clearAll();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this,NewsManagerActivity.class));
            }
        });
    }
    private void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("title",title.getText().toString());
        map.put("author",author.getText().toString());
        map.put("category",category.getText().toString());
        map.put("content",content.getText().toString());
        map.put("pubDate",pubDate.getText().toString());
        map.put("description",description.getText().toString());
        map.put("image_url",image_url.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("NewspaperInfo").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Error while Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll(){
        title.setText("");
        author.setText("");
        category.setText("");
        pubDate.setText("");
        description.setText("");
        content.setText("");
        image_url.setText("");
    }
}