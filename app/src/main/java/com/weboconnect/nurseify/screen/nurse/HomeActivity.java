package com.weboconnect.nurseify.screen.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityHomeBinding;
import com.weboconnect.nurseify.screen.nurse.ui.AccountFragment;
import com.weboconnect.nurseify.screen.nurse.ui.BrowseFragment;
import com.weboconnect.nurseify.screen.nurse.ui.MessageFragment;
import com.weboconnect.nurseify.screen.nurse.ui.MyJobFragment;
import com.weboconnect.nurseify.screen.nurse.ui.NotificationFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    ArrayList<Integer> mFragmentInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        resetBottomBar(1);
        binding.jobs.setOnClickListener(new View.OnClickListener() {
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
        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBottomBar(3);
            }
        });
        binding.notification.setOnClickListener(new View.OnClickListener() {
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

    void resetBottomBar(int position) {
        resetNavigation();
        mFragmentInt.add(position);
        switch (position) {
            case 1:
                binding.jobText.setTextColor(Color.parseColor("#3493D3"));
                binding.jobIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new BrowseFragment(), "1");
                break;
            case 2:
                binding.myJobsText.setTextColor(Color.parseColor("#3493D3"));
                binding.myJobsIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new MyJobFragment(), "1");
                break;
            case 3:
                binding.messageText.setTextColor(Color.parseColor("#3493D3"));
                binding.messageIcon.setColorFilter(Color.parseColor("#3493D3"));
                changeView(new MessageFragment(), "1");
                break;
            case 4:
                binding.notificationText.setTextColor(Color.parseColor("#3493D3"));
                binding.notificationIcon.setColorFilter(Color.parseColor("#3493D3"));
                binding.notificationView.setBackground(ContextCompat.getDrawable(HomeActivity.this, R.drawable.btn_accept));
                changeView(new NotificationFragment(), "1");
                break;
            case 5:
                binding.accountText.setTextColor(Color.parseColor("#3493D3"));
                binding.accountIcon.setBorderColor(Color.parseColor("#3792D3"));
                changeView(new AccountFragment(), "1");
                break;
        }
    }

    void resetNavigation() {
        binding.jobText.setTextColor(Color.parseColor("#000000"));
        binding.myJobsText.setTextColor(Color.parseColor("#000000"));
        binding.messageText.setTextColor(Color.parseColor("#000000"));
        binding.notificationText.setTextColor(Color.parseColor("#000000"));
        binding.accountText.setTextColor(Color.parseColor("#000000"));
        binding.jobIcon.setColorFilter(Color.parseColor("#000000"));
        binding.myJobsIcon.setColorFilter(Color.parseColor("#000000"));
        binding.messageIcon.setColorFilter(Color.parseColor("#000000"));
        binding.notificationIcon.setColorFilter(Color.parseColor("#000000"));
        binding.notificationView.setBackground(ContextCompat.getDrawable(HomeActivity.this, R.drawable.btn_reject));
        binding.accountIcon.setBorderColor(Color.parseColor("#FFFFFF"));
    }

    void changeView(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame, fragment, tag)
                .commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mFragmentInt.size() > 1) {
            mFragmentInt.remove(mFragmentInt.size() - 1);
            resetBottomBar(mFragmentInt.size() - 1);
        } else {
            finish();
        }
    }
}