package com.example.newspaper.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserManagerAdapter extends RecyclerView.Adapter<UserManagerAdapter.myviewhoder>{
    ArrayList<UserModel> datalist;
    private FirebaseFirestore db ;

    public UserManagerAdapter(ArrayList<UserModel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_usermanager_item,parent,false);
        return new myviewhoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewhoder holder, int position) {
        holder.t1.setText(datalist.get(position).getName());
        holder.t2.setText(datalist.get(position).getEmail());
        holder.t3.setText(datalist.get(position).getPhoneNumber());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        db = FirebaseFirestore.getInstance();
                        db.collection("User")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()&&!task.getResult().isEmpty()){
                                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                            String documentID = documentSnapshot.getId();
                                            db.collection("User")
                                                    .document(documentID)
                                                    .delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(holder.t2.getContext(), "Success delete", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(holder.t1.getContext(), "Some error occur", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        }else {
                                            Toast.makeText(holder.t1.getContext(), "Fair", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cannel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.t1.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return datalist.size();
    }
    class myviewhoder extends RecyclerView.ViewHolder{
        TextView t1,t2,t3;
        Button delete;
        public myviewhoder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.nametext);
            t2=itemView.findViewById(R.id.emailtext);
            t3=itemView.findViewById(R.id.phonenumbertext);
            delete=itemView.findViewById(R.id.btnDelete);

        }
    }
}