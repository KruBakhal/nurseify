package com.weboconnect.nurseify.screen.facility.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveFAdapter;
import com.weboconnect.nurseify.adapter.PostedAdapter;
import com.weboconnect.nurseify.adapter.TabAdapter;
import com.weboconnect.nurseify.databinding.FragmentMyJobsFBinding;
import com.weboconnect.nurseify.screen.facility.browse.Active_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.browse.Nurse_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.browse.Offered_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.browse.Past_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.my_jobs.Active_Jobs_Fragment;
import com.weboconnect.nurseify.screen.facility.my_jobs.Complete_Jobs_Fragment;
import com.weboconnect.nurseify.screen.facility.my_jobs.Posted_Jobs_Fragment;

public class MyJobFFragment extends Fragment {
    String id;
  public   FragmentMyJobsFBinding binding;
    View view;
    private TabAdapter adapter;

    public MyJobFFragment() {
    }

    public MyJobFFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_jobs_f, null, false);
//        binding.recyclerViewJobs.setAdapter(new PostedAdapter(getActivity()));
        getTabList();
        binding.textOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_tab(0);
            }
        });
        binding.textActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_tab(1);
            }
        });
        binding.textCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_tab(2);
            }
        });

        return view = binding.getRoot();
    }

    private void change_tab(int i) {

        switch (i) {

            case 0:
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textOffered.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
//                binding.recyclerViewJobs.setAdapter(new PostedAdapter(getActivity()));
                binding.viewPager.setCurrentItem(0);
                break;
            case 1:
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setTextColor(Color.parseColor("#8A4999"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textCompleted.setTextColor(Color.parseColor("#000000"));
//                binding.recyclerViewJobs.setAdapter(new ActiveFAdapter(getActivity()));
                binding.viewPager.setCurrentItem(1);
                break;
            case 2:
                binding.textCompleted.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textCompleted.setTextColor(Color.parseColor("#8A4999"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.viewPager.setCurrentItem(2);
                break;
        }
    }

    private void getTabList() {
        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new Posted_Jobs_Fragment(), "Posted");
        adapter.addFragment(new Active_Jobs_Fragment(), "Active Job");
        adapter.addFragment(new Complete_Jobs_Fragment(), "Complete");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                change_tab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}