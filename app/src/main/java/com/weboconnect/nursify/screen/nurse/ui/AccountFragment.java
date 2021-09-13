package com.weboconnect.nursify.screen.nurse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.databinding.FragmentAccountBinding;
import com.weboconnect.nursify.screen.nurse.HourlyRateActivity;
import com.weboconnect.nursify.screen.nurse.PersonalDetailsActivity;
import com.weboconnect.nursify.screen.nurse.RoleActivity;
import com.weboconnect.nursify.screen.nurse.SettingActivity;
import com.weboconnect.nursify.screen.nurse.WorkHistoryActivity;

public class AccountFragment extends Fragment {
    String id;
    FragmentAccountBinding binding;
    View view;
    public AccountFragment(){ }
    public AccountFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_account, null, false);
        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(), PersonalDetailsActivity.class));
            }
        });
        binding.layoutPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonalDetailsActivity.class));
            }
        });
        binding.layoutHourly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HourlyRateActivity.class));
            }
        });
        binding.layoutRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RoleActivity.class));
            }
        });
        binding.layoutWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WorkHistoryActivity.class));
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