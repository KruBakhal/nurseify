package com.nurseify.app.screen.nurse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nurseify.app.AppController;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityHomeBinding;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.screen.nurse.ui.AccountFragment;
import com.nurseify.app.screen.nurse.ui.BrowseFragment;
import com.nurseify.app.screen.nurse.ui.MessageNurseFragment;
import com.nurseify.app.screen.nurse.ui.MyJobFragment;
import com.nurseify.app.screen.nurse.ui.NotificationFragment;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    public ActivityHomeBinding binding;
    ArrayList<Integer> mFragmentInt = new ArrayList<>();
    BrowseFragment browseFragment;// = new BrowseFragment();
    MyJobFragment myJobFragment;// = new MyJobFragment();
    MessageNurseFragment messageFragment;//= new MessageFragment();
    NotificationFragment notificationFragment;//= new NotificationFragment();
    AccountFragment accountFragment;//= new AccountFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active;//= browseFragment;

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
                .placeholder(R.drawable.person)
                .error(R.drawable.person)
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
                    messageFragment = new MessageNurseFragment();
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
        if (!new SessionManager(this).get_NotificationToggle()) {
            binding.notification.setVisibility(View.GONE);
        } else {
            binding.notification.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 223 && resultCode == RESULT_OK) {
            if (!new SessionManager(this).get_NotificationToggle()) {
                binding.notification.setVisibility(View.GONE);
            } else {
                binding.notification.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == Constant.REQUEST_EDIT && resultCode == RESULT_OK) {
            if (AppController.isEdit_Result) {
                AppController.isEdit_Result = false;
                if (myJobFragment != null) {
                    myJobFragment.refresh();
                }
            }
        }
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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(Constant.USER_NODE)
                .child(new SessionManager(HomeActivity.this).get_user_register_Id());

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() == null) {
//                        create_user_inside_firebase(new SessionManager(getContext()).get_User());
                    } else {
                        try {
                            HashMap<String, Object> sdsd = new HashMap<>();
                            sdsd.put("status", status);
                            String userid = new SessionManager(HomeActivity.this).get_user_register_Id();
                            FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                                    .child(userid).child(Constant.ONLINE_STATUS)
                                    .setValue(status);
                        } catch (Exception e) {
                            Log.d("TAG", "update_user_status: " + e.getMessage());
                        }
                    }
                } catch (Exception exception) {
                    Log.d("TAG_check_User_exist", "onDataChange: " + exception.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        update_user_status(false);
    }
}