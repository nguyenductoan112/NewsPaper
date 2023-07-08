package com.example.newspaper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsManagerAdapter extends FirebaseRecyclerAdapter<NewsModel, NewsManagerAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NewsManagerAdapter(@NonNull FirebaseRecyclerOptions<NewsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull NewsModel model) {
        holder.title.setText(model.getTitle());
        holder.author.setText(model.getAuthor());
        holder.pubDate.setText(model.getPubDate());

        // tải và hiện ảnh
        Glide.with(holder.img.getContext())
                .load(model.getImage_url())
                .placeholder(R.drawable.no_image)
                .circleCrop()
                .error(R.drawable.no_image)
                .into(holder.img);

        //xu ly
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,ViewGroup.LayoutParams.WRAP_CONTENT)
                        .create();

                //dialogPlus.show();
                View view1 = dialogPlus.getHolderView();

                EditText title = view1.findViewById(R.id.txttie);
                EditText author = view1.findViewById(R.id.txtauthor);
                EditText pubDate = view1.findViewById(R.id.txtaddr);
                EditText image_url = view1.findViewById(R.id.txtImage_url);
                EditText category = view1.findViewById(R.id.txtcategory);
                EditText description = view1.findViewById(R.id.txtdescription);
                EditText content = view1.findViewById(R.id.txtcontent);

                Button btnUpdate = view1.findViewById(R.id.btnUpdate);
                title.setText(model.getTitle());
                author.setText(model.getAuthor());
                pubDate.setText(model.getPubDate());
                image_url.setText(model.getImage_url());
                category.setText(model.getCategory());
                description.setText(model.getDescription());
                content.setText(model.getContent());

                dialogPlus.show();
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("title",title.getText().toString());
                        map.put("author",author.getText().toString());
                        map.put("date",pubDate.getText().toString());
                        map.put("image_url",image_url.getText().toString());
                        map.put("category",category.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("content",content.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("NewspaperInfo")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.title.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.title.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("NewspaperInfo").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cannel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.title.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_newsmanager_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView title,category,pubDate,author,description, content,imageUrl;
        Button btnEdit,btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.titleText);
            author=itemView.findViewById(R.id.authorText);
            pubDate=itemView.findViewById(R.id.pubDateText);

            //xu ly button edit and delete
            btnEdit =  itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById((R.id.btnDelete));
        }
    }
}
