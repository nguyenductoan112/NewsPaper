package com.example.newspaper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText title,author,category,Pubdate, description, content,image_url;
    Button btnAdd, btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_add);

        title=(EditText)findViewById(R.id.txttitleadd);
        author=(EditText)findViewById(R.id.txtauthoradd);
        category=(EditText)findViewById(R.id.txtcategoryadd);
        Pubdate=(EditText)findViewById(R.id.txtpubDateadd);
        description=(EditText)findViewById(R.id.txtdescriptionadd);
        content=(EditText)findViewById(R.id.txtcontentadd);
        image_url=(EditText)findViewById(R.id.txtimage_urladd);

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnBack=(Button)findViewById(R.id.btnBack);


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
                finish();
            }
        });
    }
    private void insertData(){
        Map<String,Object> map = new HashMap<>();
        map.put("title",title.getText().toString());
        map.put("author",author.getText().toString());
        map.put("category",category.getText().toString());
        map.put("content",content.getText().toString());
        map.put("date",Pubdate.getText().toString());
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
        Pubdate.setText("");
        description.setText("");
        content.setText("");
        image_url.setText("");
    }
}