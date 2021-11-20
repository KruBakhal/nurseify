package com.weboconnect.nurseify.screen.nurse.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveAdapter;
import com.weboconnect.nurseify.adapter.CompletedAdapter;
import com.weboconnect.nurseify.adapter.FacilityAdapter;
import com.weboconnect.nurseify.adapter.JobAdapter;
import com.weboconnect.nurseify.adapter.OfferedAdapter;
import com.weboconnect.nurseify.databinding.FragmentBrowseBinding;
import com.weboconnect.nurseify.databinding.FragmentMyJobsBinding;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.SignupDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.Utils;
import com.weboconnect.nurseify.webService.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobFragment extends Fragment {
    String id;
    FragmentMyJobsBinding binding;
    View view;

    public MyJobFragment() {
    }

    public MyJobFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_jobs, null, false);
        binding.recyclerViewJobs.setAdapter(new JobAdapter(getActivity()));
        binding.textOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textOffered.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setAdapter(new OfferedAdapter(getActivity()));
            }
        });

        binding.textActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setTextColor(Color.parseColor("#8A4999"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setAdapter(new ActiveAdapter(getActivity()));
            }
        });

        binding.textCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.recyclerViewJobs.setAdapter(new CompletedAdapter(getActivity()));
            }
        });
        return view = binding.getRoot();
    }


}