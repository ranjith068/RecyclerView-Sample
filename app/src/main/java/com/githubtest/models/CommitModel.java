package com.githubtest.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by rajesh on 29/4/17.
 */

public class CommitModel {

    public String html_url;

    public Author author;

    public List<Parents> parents = new ArrayList<>();

    public Commit commit;

    public String sha;

    public String comments_url;

    public Committer committer;

    public String url;


//    @Override
//    public int compareTo(@NonNull CommitModel o) {
//        return author.compareTo(o.author);
//    }
}
