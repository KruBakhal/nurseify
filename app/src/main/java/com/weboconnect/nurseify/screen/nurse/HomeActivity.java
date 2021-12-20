package com.weboconnect.nurseify.screen.nurse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.FirebaseDatabase;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.ActivityHomeBinding;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;
import com.weboconnect.nurseify.screen.nurse.ui.AccountFragment;
import com.weboconnect.nurseify.screen.nurse.ui.BrowseFragment;
import com.weboconnect.nurseify.screen.nurse.ui.MessageFragment;
import com.weboconnect.nurseify.screen.nurse.ui.MyJobFragment;
import com.weboconnect.nurseify.screen.nurse.ui.NotificationFragment;
import com.weboconnect.nurseify.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    public ActivityHomeBinding binding;
    ArrayList<Integer> mFragmentInt = new ArrayList<>();
    BrowseFragment browseFragment;// = new BrowseFragment();
    MyJobFragment myJobFragment;// = new MyJobFragment();
    MessageFragment messageFragment;//= new MessageFragment();
    NotificationFragment notificationFragment;//= new NotificationFragment();
    AccountFragment accountFragment;//= new AccountFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active ;//= browseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        setData();
        resetBottomBar(1);
        click();
    }

    private void setData() {
        UserProfileData userProfileData = new SessionManager(HomeActivity.this).get_User();
        Glide.with(HomeActivity.this).load(userProfileData.getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d("TAG", "onLoadFailed: 1 " + e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(binding.accountIcon);
    }

    private void click() {
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
                if (browseFragment == null) {
                    browseFragment = new BrowseFragment();
                    fm.beginTransaction().add(R.id.frame, browseFragment, "1").commit();
                    active = browseFragment;
                }
                changeView(browseFragment, "1");
                break;
            case 2:
                binding.myJobsText.setTextColor(Color.parseColor("#3493D3"));
                binding.myJobsIcon.setColorFilter(Color.parseColor("#3493D3"));
                if (myJobFragment == null) {
                    myJobFragment = new MyJobFragment();
                    fm.beginTransaction().add(R.id.frame, myJobFragment, "2").hide(myJobFragment).commit();

                }
                changeView(myJobFragment, "2");
                break;
            case 3:
                binding.messageText.setTextColor(Color.parseColor("#3493D3"));
                binding.messageIcon.setColorFilter(Color.parseColor("#3493D3"));
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    fm.beginTransaction().add(R.id.frame, messageFragment, "3").hide(messageFragment).commit();

                }
                changeView(messageFragment, "3");
                break;
            case 4:
                binding.notificationText.setTextColor(Color.parseColor("#3493D3"));
                binding.notificationIcon.setColorFilter(Color.parseColor("#3493D3"));
                binding.notificationView.setBackground(ContextCompat.getDrawable(HomeActivity.this, R.drawable.btn_accept));
                if (notificationFragment == null) {
                    notificationFragment = new NotificationFragment();
                    fm.beginTransaction().add(R.id.frame, notificationFragment, "4").hide(notificationFragment).commit();

                }
                changeView(notificationFragment, "4");
                break;
            case 5:
                binding.accountText.setTextColor(Color.parseColor("#3493D3"));
                binding.accountIcon.setBorderColor(Color.parseColor("#3792D3"));
                if (accountFragment == null) {
                    accountFragment = new AccountFragment();
                    fm.beginTransaction().add(R.id.frame, accountFragment, "5").hide(accountFragment).commit();
                }
                changeView(accountFragment, "5");
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
       /* getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frame, fragment, tag)
                .commit();*/
        fm.beginTransaction().hide(active).show(fragment).commit();
        active = fragment;

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

    @Override
    public void onResume() {
        super.onResume();
        update_user_status(true);
    }

    private void update_user_status(boolean status) {
        /*try {
            HashMap<String, Object> sdsd = new HashMap<>();
            sdsd.put("status", status);
            String userid = new SessionManager(HomeActivity.this).get_User().getId();
            FirebaseDatabase.getInstance().getReference("users")
                    .child(userid).child("status").setValue(status);
        } catch (Exception e) {
            Log.d("TAG", "update_user_status: " + e.getMessage());
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        update_user_status(false);
    }

}