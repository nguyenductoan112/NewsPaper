package com.example.newspaper.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserManagerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserModel> datalist;
    FirebaseFirestore db;
    UserManagerAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanager);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<>();
        adapter=new UserManagerAdapter(datalist);
        recyclerView.setAdapter(adapter);

        db=FirebaseFirestore.getInstance();
        db.collection("User").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<DocumentSnapshot>list= (ArrayList<DocumentSnapshot>) queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d: list)
                        {
                            UserModel obj = d.toObject(UserModel.class);
                            datalist.add(obj);
                        }
                        //update Adapter
                        adapter.notifyDataSetChanged();

                    }
                });


    }

}