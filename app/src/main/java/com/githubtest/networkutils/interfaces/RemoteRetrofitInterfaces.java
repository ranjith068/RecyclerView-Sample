package com.githubtest.networkutils.interfaces;

import com.githubtest.models.CommitModel;
import com.githubtest.models.CommitServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rajesh on 29/4/17.
 */

public interface RemoteRetrofitInterfaces {

    @GET("repos/rails/rails/commits")
    Call<List<CommitModel>> getCommits();
}
