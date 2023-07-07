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

public class NewsAdapter extends FirebaseRecyclerAdapter<NewsModel, NewsAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NewsAdapter(@NonNull FirebaseRecyclerOptions<NewsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull NewsModel model) {
        holder.title.setText(model.getTitle());
        holder.category.setText(model.getCategory());
        holder.Pubdate.setText(model.getPubDate());
        // tải và hiện ảnh
        Glide.with(holder.img.getContext())
                .load(model.getImage_url())
                .placeholder(com.firebase.ui.database.R.drawable.googleg_standard_color_18)
                .circleCrop()
                .error(com.google.firebase.firestore.ktx.R.drawable.googleg_standard_color_18)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView title,category,Pubdate,author,description, content,image_url;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img);
            title=(TextView)itemView.findViewById(R.id.Titletext);
            category=(TextView)itemView.findViewById(R.id.Categorytext);
            Pubdate=(TextView)itemView.findViewById(R.id.Pubdatetext);
        }
    }
}
