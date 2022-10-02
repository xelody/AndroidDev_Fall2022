package edu.northeastern.numad22fa_peiyaoxin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class LinkCollectorActivity extends AppCompatActivity implements View.OnClickListener, InputFragment.OnInputListener{
    private ArrayList<Link> linkList;
    private RecyclerView linkRecyclerView;
    private FloatingActionButton floatingActionButton;
    private RecyclerView.Adapter adapter;
    private LinkAdaptor mAdapter;


    @Override
    public void sendInput(Link link) {
        linkList.add(link);
        adapter.notifyDataSetChanged();
        Snackbar.make(findViewById(R.id.myLinkCollectorLayout),
                    "Successfully Created", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        hideFragment();

        linkList = new ArrayList<>();
        if (savedInstanceState != null) {
            linkList = savedInstanceState.getParcelableArrayList("links");
        }

        linkRecyclerView = findViewById(R.id.link_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        linkRecyclerView.setLayoutManager(layoutManager);
        adapter = new LinkAdaptor(linkList, this);
        linkRecyclerView.setAdapter(adapter);

        floatingActionButton = findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(this);

        enableSwipeToDeleteAndUndo();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("links", linkList);
        super.onSaveInstanceState(outState);
    }

    ItemTouchHelper.SimpleCallback callback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar.make(findViewById(R.id.myLinkCollectorLayout),
                    "Link Deleted!", Snackbar.LENGTH_SHORT).show();

            linkList.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Link link = mAdapter.getData().get(position);

                mAdapter.removeLink(position);


                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.myLinkCollectorLayout), "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreLink(link, position);
                        linkRecyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(callback);
        itemTouchhelper.attachToRecyclerView(linkRecyclerView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.add_fab) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            Fragment inputFragment = manager.findFragmentById(R.id.fragment_input);
            if (inputFragment != null) {
                ft.show(inputFragment);
                ft.commit();
            }
            getSupportFragmentManager().executePendingTransactions();
//            linkRecyclerView.setVisibility(View.GONE);
        }
    }

    public void hideFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment inputFragment = manager.findFragmentById(R.id.fragment_input);
        if (inputFragment != null) {
            ft.hide(inputFragment);
            ft.commit();
        }
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