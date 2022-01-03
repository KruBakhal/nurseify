package com.weboconnect.nurseify.screen.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.facility.add_job_fragment.Add_Job_1_Fragment;
import com.weboconnect.nurseify.screen.facility.add_job_fragment.Add_Job_2_Fragment;
import com.weboconnect.nurseify.screen.facility.ui.AccountFFragment;
import com.weboconnect.nurseify.screen.facility.ui.BrowseFFragment;
import com.weboconnect.nurseify.screen.facility.ui.MessageFragment;
import com.weboconnect.nurseify.screen.facility.ui.MyJobFFragment;
import com.weboconnect.nurseify.screen.facility.viewModel.Add_Job_ViewModel;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatus;
import com.weboconnect.nurseify.screen.facility.viewModel.DialogStatusMessage;
import com.weboconnect.nurseify.screen.facility.viewModel.RegistrationViewModel;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class Add_Jobs_Activity extends AppCompatActivity {
    Context context;
    FragmentManager fragmentManager;
    Add_Job_1_Fragment add_job_1_fragment;
    Add_Job_2_Fragment add_job_2_fragment;
    ArrayList<Integer> mFragmentInt = new ArrayList<>();
    private Fragment active;
    public Add_Job_ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jobs);
        context=this;
        fragmentManager = getSupportFragmentManager();
        viewModel = new ViewModelProvider(this).get(Add_Job_ViewModel.class);
        observer_view();
        resetBottomBar(1);
    }

    private void observer_view() {
        viewModel.do_DismissDialog().observe(this, new Observer<DialogStatusMessage>() {
            @Override
            public void onChanged(DialogStatusMessage dialogStatus) {
                switch (dialogStatus.getDialogStatus()) {
                    case Close:
                        finish();
                        break;
                    case Dismiss:
                        if (dialogStatus.getDialogType() == 1) {
                            resetBottomBar(2);
                        }
                        break;
                    case Done:
                        if (dialogStatus.getDialogType() == 2) {
                            setResult(RESULT_OK);
                            finish();
                        }
                        break;
                    case Cancel:
                        if (dialogStatus.getDialogType() == 2) {
                            resetBottomBar(1);
                        }
                        break;
                }
            }
        });
        viewModel.getToastMesssage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.displayToast(context, s);
            }
        });
    }

    void resetBottomBar(int position) {
        resetNavigation();
        mFragmentInt.add(position);
        switch (position) {
            case 1:
                if (add_job_1_fragment == null) {
                    add_job_1_fragment = Add_Job_1_Fragment.newInstance(1, "");
                    fragmentManager.beginTransaction().add(R.id.frame, add_job_1_fragment, "1").commit();
                    active = add_job_1_fragment;
                }
                changeView(add_job_1_fragment, "1");
                break;
            case 2:
                if (add_job_2_fragment == null) {
                    add_job_2_fragment = Add_Job_2_Fragment.newInstance(2, "");
                    fragmentManager.beginTransaction().add(R.id.frame, add_job_2_fragment, "2").commit();
                    active = add_job_2_fragment;
                }
                changeView(add_job_2_fragment, "2");
                break;

        }
    }

    void resetNavigation() {

    }

    void changeView(Fragment fragment, String tag) {
        fragmentManager.beginTransaction()
//                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .hide(active).show(fragment).commit();
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
}