package edu.northeastern.numad22fa_peiyaoxin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView URL;

    public LinkViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.tv_link_name);
        this.URL = itemView.findViewById(R.id.tv_link_URL);
    }
}
