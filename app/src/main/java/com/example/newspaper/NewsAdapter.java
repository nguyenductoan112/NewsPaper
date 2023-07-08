package com.example.newspaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

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
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, NewsModel model) {
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
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_item, parent, false);
        return new myViewHolder(view, itemClickListener);
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView img;
        TextView title,category,pubDate,author,description, content;
        CardView cardView;
        private OnItemClickListener listener;

        public myViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.titleText);
            author=itemView.findViewById(R.id.authorText);
            pubDate=itemView.findViewById(R.id.pubDateText);
            description=itemView.findViewById(R.id.descriptionText);
            content=itemView.findViewById(R.id.contentText);
            cardView = itemView.findViewById(R.id.main_container);
            this.listener = listener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(NewsModel model);
    }
    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
