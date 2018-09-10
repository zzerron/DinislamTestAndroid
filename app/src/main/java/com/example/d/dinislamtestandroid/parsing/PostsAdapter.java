package com.example.d.dinislamtestandroid.parsing;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.d.dinislamtestandroid.R;

import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Quote> posts;

    public PostsAdapter(List<Quote> posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Quote post = posts.get(position);
        holder.id.setText("ID: " + post.getId());
        holder.description.setText(post.getDescription());
        holder.time.setText(post.getTime());
        holder.rating.setText("rating " + post.getRating());
    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView description;
        TextView time;
        TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.postitem_id);
            description = (TextView) itemView.findViewById(R.id.postitem_description);
            time = (TextView) itemView.findViewById(R.id.postitem_time);
            rating = (TextView) itemView.findViewById(R.id.postitem_rating);
        }
    }
}
