package com.nurseify.app.screen.facility;

import static com.nurseify.app.utils.Constant.REQUEST_CODE_ADD_JOB;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nurseify.app.AppController;
import com.nurseify.app.MainActivity;
import com.nurseify.app.R;
import com.nurseify.app.databinding.ActivityHomeFBinding;
import com.nurseify.app.screen.facility.ui.AccountFFragment;
import com.nurseify.app.screen.facility.ui.BrowseFFragment;
import com.nurseify.app.screen.facility.ui.MessageFacilityFragment;
import com.nurseify.app.screen.facility.ui.MyJobFFragment;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFActivity extends AppCompatActivity {

    ActivityHomeFBinding binding;
    ArrayList<Integer> mFragmentInt = new ArrayList<>();
    public BrowseFFragment browseFFragment;
    public MyJobFFragment myJobFFragment;
    public MessageFacilityFragment messageFragment;
    public AccountFFragment accountFFragment;
    public FragmentManager fm = null;
    private Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(HomeFActivity.this, R.layout.activity_home_f);
        fm = getSupportFragmentManager();
        Clear_GlideCaches();
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

    public void Clear_GlideCaches() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.get(HomeFActivity.this).clearMemory();
            }
        }, 0);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Glide.get(HomeFActivity.this).clearDiskCache();
            }
        });
    }

    void resetBottomBar(int position) {
        resetNavigation();
        mFragmentInt.add(position);
        switch (position) {
            case 1:
                binding.browseText.setTextColor(Color.parseColor("#3493D3"));
                binding.browseIcon.setColorFilter(Color.parseColor("#3493D3"));
                if (browseFFragment == null) {
                    browseFFragment = new BrowseFFragment();
                    fm.beginTransaction().add(R.id.frame, browseFFragment, "1").commit();
                    active = browseFFragment;
                }
                changeView(browseFFragment, "1");
                break;
            case 2:
                binding.myJobsText.setTextColor(Color.parseColor("#3493D3"));
                binding.myJobsIcon.setColorFilter(Color.parseColor("#3493D3"));
                if (myJobFFragment == null) {
                    myJobFFragment = new MyJobFFragment();
                    fm.beginTransaction().add(R.id.frame, myJobFFragment, "2").hide(myJobFFragment).commit();

                }
                changeView(myJobFFragment, "2");
                break;
            case 3:
                startActivityForResult(new Intent(HomeFActivity.this, Add_Jobs_Activity.class), REQUEST_CODE_ADD_JOB);
                break;
            case 4:
                binding.messageText.setTextColor(Color.parseColor("#3493D3"));
                binding.messageIcon.setColorFilter(Color.parseColor("#3493D3"));
                if (messageFragment == null) {
                    messageFragment = new MessageFacilityFragment();
                    fm.beginTransaction().add(R.id.frame, messageFragment, "3").hide(messageFragment).commit();
                }
                changeView(messageFragment, "3");
                break;
            case 5:
                binding.accountText.setTextColor(Color.parseColor("#3493D3"));
                binding.accountIcon.setColorFilter(Color.parseColor("#3493D3"));
                if (accountFFragment == null) {
                    accountFFragment = new AccountFFragment();
                    fm.beginTransaction().add(R.id.frame, accountFFragment, "4").hide(accountFFragment).commit();
                }
                changeView(accountFFragment, "4");
                break;
        }
    }

    void resetNavigation() {
        binding.browseText.setTextColor(Color.parseColor("#000000"));
        binding.myJobsText.setTextColor(Color.parseColor("#000000"));
        binding.messageText.setTextColor(Color.parseColor("#000000"));
        binding.accountText.setTextColor(Color.parseColor("#000000"));
        binding.browseIcon.setColorFilter(Color.parseColor("#000000"));
        binding.myJobsIcon.setColorFilter(Color.parseColor("#000000"));
        binding.messageIcon.setColorFilter(Color.parseColor("#000000"));
        binding.accountIcon.setColorFilter(Color.parseColor("#000000"));
    }

    void changeView(Fragment fragment, String tag) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_JOB && resultCode == RESULT_OK) {
            AppController.isEdit_Result = true;
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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(Constant.USER_NODE)
                .child(new SessionManager(HomeFActivity.this).get_user_register_Id());

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
                            String userid = new SessionManager(HomeFActivity.this).get_user_register_Id();
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