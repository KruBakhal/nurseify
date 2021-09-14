package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityHomeFBinding;
import com.weboconnect.nurseify.screen.facility.ui.AccountFFragment;
import com.weboconnect.nurseify.screen.facility.ui.BrowseFFragment;
import com.weboconnect.nurseify.screen.facility.ui.MessageFragment;
import com.weboconnect.nurseify.screen.facility.ui.MyJobFFragment;

import java.util.ArrayList;

public class HomeFActivity extends AppCompatActivity {
    ActivityHomeFBinding binding;
    ArrayList<Integer> mFragmentInt = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeFActivity.this,R.layout.activity_home_f);
        resetBottomBar(1);
        binding.browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomBar(1);
            }
        });
        binding.myJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomBar(2);
            }
        });
        binding.addJpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomBar(3);
            }
        });
        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomBar(4);
            }
        });
        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomBar(5);
            }
        });
    }

    void resetBottomBar(int position){
        resetNavigation();
        mFragmentInt.add(position);
        switch (position){
            case 1:
                binding.browseText.setTextColor(Color.parseColor("#3493D3"));
                binding.browseIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new BrowseFFragment(),"1");
                break;
            case 2:
                binding.myJobsText.setTextColor(Color.parseColor("#3493D3"));
                binding.myJobsIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new MyJobFFragment(),"1");
                break;
            case 3:
                startActivity(new Intent(HomeFActivity.this, AddJobActivity1.class));
                break;
            case 4:
                binding.messageText.setTextColor(Color.parseColor("#3493D3"));
                binding.messageIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new MessageFragment(),"1");
                break;
            case 5:
                binding.accountText.setTextColor(Color.parseColor("#3493D3"));
                binding.accountIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new AccountFFragment(),"1");
                break;
        }
    }
    void resetNavigation(){
        binding.browseText.setTextColor(Color.parseColor("#000000"));
        binding.myJobsText.setTextColor(Color.parseColor("#000000"));
        binding.messageText.setTextColor(Color.parseColor("#000000"));
        binding.accountText.setTextColor(Color.parseColor("#000000"));
        binding.browseIcon.setColorFilter(Color.parseColor("#000000"));
        binding.myJobsIcon.setColorFilter(Color.parseColor("#000000"));
        binding.messageIcon.setColorFilter(Color.parseColor("#000000"));
        binding.accountIcon.setColorFilter(Color.parseColor("#000000"));
       }
    void changeView(Fragment fragment, String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame, fragment,tag)
                .commit();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFragmentInt.size()>1){
            mFragmentInt.remove(mFragmentInt.size()-1);
            resetBottomBar(mFragmentInt.size()-1);
        }else {
            finish();
        }
    }
}