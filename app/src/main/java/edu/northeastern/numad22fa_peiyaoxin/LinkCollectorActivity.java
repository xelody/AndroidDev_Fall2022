package edu.northeastern.numad22fa_peiyaoxin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity implements View.OnClickListener{
    private static List<Link> linkList;
    private RecyclerView linkRecyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        hideFragment();

        linkList = new ArrayList<>();

        linkList.add(new Link("John Doe", "www.google.com"));

        linkRecyclerView = findViewById(R.id.link_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        linkRecyclerView.setLayoutManager(layoutManager);
        LinkAdaptor adapter = new LinkAdaptor(linkList, this);
        linkRecyclerView.setAdapter(adapter);

        floatingActionButton = findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.add_fab) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            Fragment inputFragment = manager.findFragmentById(R.id.fragment_input);
            ft.show(inputFragment);
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
//            linkRecyclerView.setVisibility(View.GONE);
        }
    }

    public void hideFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment inputFragment = manager.findFragmentById(R.id.fragment_input);
        ft.hide(inputFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_input);
        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.remove(fragment);
            ft.commit();
        } else {
            super.onBackPressed();
        }
    }
}