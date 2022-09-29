package edu.northeastern.numad22fa_peiyaoxin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LinkAdaptor extends RecyclerView.Adapter<LinkViewHolder> {

    private final List<Link> link;
    private final Context context;

    /**
     * Creates a LinkAdapter with the provided arraylist of Link objects.
     *
     * @param link    arraylist of link object.
     * @param context   context of the activity used for inflating layout of the viewholder.
     */
    public LinkAdaptor(List<Link> link, Context context) {
        this.link = link;
        this.context = context;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_link, null));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        holder.name.setText(link.get(position).getName());
        holder.URL.setText(link.get(position).getURL());

        holder.itemView.setOnClickListener(view ->
            Toast.makeText(context, link.get(position).getName(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return link.size();
    }
}
