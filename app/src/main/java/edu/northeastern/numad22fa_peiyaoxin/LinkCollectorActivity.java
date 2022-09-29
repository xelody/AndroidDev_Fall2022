package edu.northeastern.numad22fa_peiyaoxin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity {
    RecyclerView linkRecyclerView;
    List<Link> linkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        linkList = new ArrayList<>();

        linkList.add(new Link("John Doe", "www.google.com"));

        linkRecyclerView = findViewById(R.id.link_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        linkRecyclerView.setLayoutManager(layoutManager);
        LinkAdaptor adapter =new LinkAdaptor(linkList,this);
        linkRecyclerView.setAdapter(adapter);
    }
}