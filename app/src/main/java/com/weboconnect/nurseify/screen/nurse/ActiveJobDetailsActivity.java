package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityActiveJobDetailsBinding;

import java.util.ArrayList;

public class ActiveJobDetailsActivity extends AppCompatActivity {

    ActivityActiveJobDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ActiveJobDetailsActivity.this,R.layout.activity_active_job_details);
        ratingDailog();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void ratingDailog(){
        final View loc = getLayoutInflater().from(ActiveJobDetailsActivity.this).inflate(R.layout.dialog_rating, null);
        final Dialog dialog = new Dialog(ActiveJobDetailsActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        ImageView close = dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text_submit = dialog.findViewById(R.id.text_submit);
        text_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDailogDone();
                dialog.dismiss();
            }
        });
        ArrayList<TextView> onBoard = new ArrayList<>();
        onBoard.add((TextView) dialog.findViewById(R.id.com1));
        onBoard.add((TextView) dialog.findViewById(R.id.com2));
        onBoard.add((TextView) dialog.findViewById(R.id.com3));
        onBoard.add((TextView) dialog.findViewById(R.id.com4));
        onBoard.add((TextView) dialog.findViewById(R.id.com5));
        onBoard.add((TextView) dialog.findViewById(R.id.com6));
        onBoard.add((TextView) dialog.findViewById(R.id.com7));
        onBoard.add((TextView) dialog.findViewById(R.id.com8));
        onBoard.add((TextView) dialog.findViewById(R.id.com9));
        onBoard.add((TextView) dialog.findViewById(R.id.com10));
        ArrayList<TextView> teamWork = new ArrayList<>();
        teamWork.add((TextView) dialog.findViewById(R.id.beh1));
        teamWork.add((TextView) dialog.findViewById(R.id.beh2));
        teamWork.add((TextView) dialog.findViewById(R.id.beh3));
        teamWork.add((TextView) dialog.findViewById(R.id.beh4));
        teamWork.add((TextView) dialog.findViewById(R.id.beh5));
        teamWork.add((TextView) dialog.findViewById(R.id.beh6));
        teamWork.add((TextView) dialog.findViewById(R.id.beh7));
        teamWork.add((TextView) dialog.findViewById(R.id.beh8));
        teamWork.add((TextView) dialog.findViewById(R.id.beh9));
        teamWork.add((TextView) dialog.findViewById(R.id.beh10));
        ArrayList<TextView> leaderShip = new ArrayList<>();
        leaderShip.add((TextView) dialog.findViewById(R.id.res1));
        leaderShip.add((TextView) dialog.findViewById(R.id.res2));
        leaderShip.add((TextView) dialog.findViewById(R.id.res3));
        leaderShip.add((TextView) dialog.findViewById(R.id.res4));
        leaderShip.add((TextView) dialog.findViewById(R.id.res5));
        leaderShip.add((TextView) dialog.findViewById(R.id.res6));
        leaderShip.add((TextView) dialog.findViewById(R.id.res7));
        leaderShip.add((TextView) dialog.findViewById(R.id.res8));
        leaderShip.add((TextView) dialog.findViewById(R.id.res9));
        leaderShip.add((TextView) dialog.findViewById(R.id.res10));
        ArrayList<TextView> toolsJob = new ArrayList<>();
        leaderShip.add((TextView) dialog.findViewById(R.id.fas1));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas2));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas3));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas4));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas5));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas6));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas7));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas8));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas9));
        leaderShip.add((TextView) dialog.findViewById(R.id.fas10));
        for(int i =0;i<onBoard.size();i++){
            int finalI = i;
            onBoard.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j =0;j<onBoard.size();j++){
                        onBoard.get(j).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating));
                    }
                    onBoard.get(finalI).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating_active));
                }
            });
        }
        for(int i =0;i<teamWork.size();i++){
            int finalI = i;
            teamWork.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j =0;j<teamWork.size();j++){
                        teamWork.get(j).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating));
                    }
                    teamWork.get(finalI).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating_active));
                }
            });
        }
        for(int i =0;i<leaderShip.size();i++){
            int finalI = i;
            leaderShip.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j =0;j<leaderShip.size();j++){
                        leaderShip.get(j).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating));
                    }
                    leaderShip.get(finalI).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating_active));
                }
            });
        }
        for(int i =0;i<toolsJob.size();i++){
            int finalI = i;
            toolsJob.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j =0;j<toolsJob.size();j++){
                        toolsJob.get(j).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating));
                    }
                    toolsJob.get(finalI).setBackground(ContextCompat.getDrawable(ActiveJobDetailsActivity.this,R.drawable.bg_rating_active));
                }
            });
        }
    }
    private void ratingDailogDone(){
        final View loc = getLayoutInflater().from(ActiveJobDetailsActivity.this).inflate(R.layout.dialog_rating_done, null);
        final Dialog dialog = new Dialog(ActiveJobDetailsActivity.this,R.style.AlertDialog);
        dialog.setContentView(loc);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(true);
        dialog.show();
        TextView text_ok = dialog.findViewById(R.id.text_ok);
        text_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}