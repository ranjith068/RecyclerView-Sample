package com.githubtest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.githubtest.models.CommitModel;
import com.githubtest.networkutils.interfaces.OnRetrofitResult;

import java.util.List;

import retrofit2.Response;

/**
 * Created by rajesh on 29/4/17.
 */

public class BaseActivity extends AppCompatActivity implements OnRetrofitResult {

    public void displayAlert() {


        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Network Error");
        alertDialog
                .setMessage("Please Check Your Internet Connection and Try Again");

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                });

        alertDialog.show();
    }

    @Override
    public void onCommitResult(boolean result, Response<List<CommitModel>> code) {

    }
}
