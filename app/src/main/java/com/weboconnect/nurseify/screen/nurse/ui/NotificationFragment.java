package com.weboconnect.nurseify.screen.nurse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.MessageAdapter;
import com.weboconnect.nurseify.adapter.NotificationAdapter;
import com.weboconnect.nurseify.databinding.FragmentMessageBinding;
import com.weboconnect.nurseify.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {
    String id;
    FragmentNotificationBinding binding;
    View view;
    public NotificationFragment(){ }
    public NotificationFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, null, false);
        binding.recyclerViewJobs.setAdapter(new NotificationAdapter(getActivity()));
        return view = binding.getRoot();
    }
}