package com.githubtest.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.githubtest.R;
import com.githubtest.interfaces.RecyclersOnItemClickListener;
import com.githubtest.models.Commit;
import com.githubtest.models.CommitModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajesh on 29/4/17.
 */

public class ListAdapterHolder extends RecyclerView.Adapter<ListAdapterHolder.CustomViewHolder> {


    private List<CommitModel> mCommitList = new ArrayList<CommitModel>();

    private final Activity mActivity;
    RecyclersOnItemClickListener mRecyclersOnItemClickListener;



    public ListAdapterHolder(Activity mActivity, RecyclersOnItemClickListener listener, List<CommitModel> mCommitList) {
        this.mActivity = mActivity;
        this.mCommitList = mCommitList;
        this.mRecyclersOnItemClickListener = listener;

    }

    @Override
    public ListAdapterHolder.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ListAdapterHolder.CustomViewHolder holder, int position) {
        holder.name.setText(mCommitList.get(position).commit.author.name);
        holder.commit.setText( mCommitList.get(position).commit.author.email);

        Picasso.with(holder.image.getContext().getApplicationContext()).load( mCommitList.get(position).author.avatar_url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).fit().into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mCommitList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView name, commit;
        ImageView image;
        CardView cardView;
        LinearLayout cardLayout;
        public CustomViewHolder(View view) {
            super(view);

            cardLayout = (LinearLayout)view.findViewById(R.id.cardLayout);
            name = (TextView) view.findViewById(R.id.tvName);
            commit = (TextView) view.findViewById(R.id.tvCommit);
            image = (ImageView) view.findViewById(R.id.commitImage);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            mRecyclersOnItemClickListener.onItemClick(getPosition(), v);

        }

    }

    // for searching not completed
    public void setFilter(List<CommitModel> countryModels) {
        mCommitList = new ArrayList<>();
        mCommitList.addAll(countryModels);
        notifyDataSetChanged();
    }



}
