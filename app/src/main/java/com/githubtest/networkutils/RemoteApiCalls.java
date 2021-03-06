package com.githubtest.networkutils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.githubtest.models.CommitModel;
import com.githubtest.models.CommitServerResponse;
import com.githubtest.networkutils.interfaces.OnRetrofitResult;
import com.githubtest.networkutils.interfaces.RemoteRetrofitInterfaces;
import com.githubtest.utils.AppConstant;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rajesh on 29/4/17.
 */

@SuppressLint("LongLogTag")
public final class RemoteApiCalls {
    private static final String TAG = "RemoteApiCalls";

    public static final class Builder {
        RemoteRetrofitInterfaces mService;
        Retrofit mRetrofit;

        OnRetrofitResult OnRetrofitResultInterface;

        public Builder remoteApiCall(OnRetrofitResult OnRetrofitResultInterface) {


            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor)
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.HOST).addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            mService = mRetrofit.create(RemoteRetrofitInterfaces.class);
            this.OnRetrofitResultInterface = OnRetrofitResultInterface;
            return this;
        }


        /*login Info*/
        public void commitListCall() {




            Call<List<CommitModel>> api = mService.getCommits();


            api.enqueue(new Callback<List<CommitModel>>() {
                @Override
                public void onResponse(Call<List<CommitModel>> responseCall, Response<List<CommitModel>> response) {

//                    Log.d("response" ,response.body().toString());


                    if (response.body().size() > 0) {

                            OnRetrofitResultInterface.onCommitResult(true,  response);

                    } else {
                        OnRetrofitResultInterface.onCommitResult(false, null);


                    }


                }

                @Override
                public void onFailure(Call<List<CommitModel>> responseCall, Throwable t) {
                    t.printStackTrace();
                    OnRetrofitResultInterface.onCommitResult(false, null);

                }


            });
        }
    }



}