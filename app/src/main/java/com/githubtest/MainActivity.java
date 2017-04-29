package com.githubtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.githubtest.adapters.ListAdapterHolder;
import com.githubtest.interfaces.RecyclersOnItemClickListener;
import com.githubtest.models.CommitModel;
import com.githubtest.networkutils.RemoteApiCalls;
import com.githubtest.utils.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends BaseActivity implements RecyclersOnItemClickListener {

    ConnectionDetector connectionDetector;
    private RecyclerView mRecyclerView;
    private ListAdapterHolder adapter;
    List<CommitModel> commitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionDetector = new ConnectionDetector(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ListAdapterHolder(this,this,commitList);


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

    }

    @Override
    public void onCommitResult(boolean result, Response<List<CommitModel>> code) {

        if(result) {
            commitList.addAll(code.body());
            adapter.notifyDataSetChanged();

        }
    }
}
