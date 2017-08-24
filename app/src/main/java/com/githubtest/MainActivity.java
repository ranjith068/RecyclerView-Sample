package com.githubtest;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.githubtest.adapters.ListAdapterHolder;
import com.githubtest.interfaces.RecyclersOnItemClickListener;
import com.githubtest.models.CommitModel;
import com.githubtest.networkutils.RemoteApiCalls;
import com.githubtest.utils.ConnectionDetector;
import com.githubtest.utils.InternalStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends BaseActivity implements RecyclersOnItemClickListener , SearchView.OnQueryTextListener{

    ConnectionDetector connectionDetector;
    private RecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    List<CommitModel> commitList = new ArrayList<>();
    List<CommitModel> bookmarkList = new ArrayList<>();

    ArrayList<Integer> mFavArray = new ArrayList<Integer>();
    SearchView.OnQueryTextListener seOnQueryTextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionDetector = new ConnectionDetector(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ListAdapterHolder(this,this,commitList);

        seOnQueryTextListener = this;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

        if (connectionDetector.isConnectingToInternet()) {
//            mProgressbar.show("Loading...");
            new RemoteApiCalls.Builder().remoteApiCall(this).commitListCall();

        } else {
            displayAlert();
        }

    }

    @Override
    public void onItemClick(int position, View v) {

        if(mFavArray.contains(position)){
            Toast.makeText(this, "reoving to bookmark", Toast.LENGTH_SHORT).show();
            bookmarkList.remove(commitList.get(position));
        }else{
            Toast.makeText(this, "adding from bookmark", Toast.LENGTH_SHORT).show();
            bookmarkList.add(commitList.get(position));
            mFavArray.add(position);
        }


    }

    @Override
    public void onCommitResult(boolean result, Response<List<CommitModel>> code) {

        if(result) {
            commitList.addAll(code.body());
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                MenuItemCompat.setOnActionExpandListener(item,
                        new MenuItemCompat.OnActionExpandListener() {
                            @Override
                            public boolean onMenuItemActionCollapse(MenuItem item) {
                                // Do something when collapsed
                                adapter.setFilter(commitList);
                                return true; // Return true to collapse action view
                            }

                            @Override
                            public boolean onMenuItemActionExpand(MenuItem item) {
                                // Do something when expanded
                                return true; // Return true to expand action view
                            }
                        });
                return true;
            case R.id.action_sort:

                adapter = new ListAdapterHolder(this,this,bookmarkList);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.setAdapter(adapter);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
