package com.weboconnect.nurseify.screen.facility.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.databinding.FragmentAccountBinding;
import com.weboconnect.nurseify.databinding.FragmentAccountFBinding;
import com.weboconnect.nurseify.screen.facility.FacilityDetails1Activity;
import com.weboconnect.nurseify.screen.facility.FacilityDetails2Activity;
import com.weboconnect.nurseify.screen.nurse.HourlyRateActivity;
import com.weboconnect.nurseify.screen.nurse.PersonalDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.RoleActivity;
import com.weboconnect.nurseify.screen.nurse.SettingActivity;
import com.weboconnect.nurseify.screen.nurse.WorkHistoryActivity;

public class AccountFFragment extends Fragment {
    String id;
    FragmentAccountFBinding binding;
    View view;
    public AccountFFragment(){ }
    public AccountFFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_account_f, null, false);
        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), PersonalDetailsActivity.class));
            }
        });
        binding.layoutPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FacilityDetails1Activity.class));
            }
        });
        binding.layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });
        return view = binding.getRoot();
    }
}