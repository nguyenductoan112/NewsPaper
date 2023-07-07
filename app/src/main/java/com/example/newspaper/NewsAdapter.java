package com.example.newspaper;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

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
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.Titletext);
            category=itemView.findViewById(R.id.Categorytext);
            Pubdate=itemView.findViewById(R.id.Pubdatetext);
        }
    }
}
