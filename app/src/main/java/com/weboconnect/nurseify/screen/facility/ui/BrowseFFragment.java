package com.weboconnect.nurseify.screen.facility.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveFAdapter;
import com.weboconnect.nurseify.adapter.TabAdapter;
import com.weboconnect.nurseify.screen.facility.NotificationActivity;
import com.weboconnect.nurseify.screen.facility.browse.Active_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.browse.Nurse_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.browse.Offered_Browse_Fragment;
import com.weboconnect.nurseify.screen.facility.browse.Past_Browse_Fragment;
import com.weboconnect.nurseify.screen.nurse.adapters.JobAdapter;
import com.weboconnect.nurseify.adapter.NursesAdapter;
import com.weboconnect.nurseify.adapter.OfferedFAdapter;
import com.weboconnect.nurseify.adapter.PastAdapter;
import com.weboconnect.nurseify.databinding.FragmentBrowseFBinding;
import com.weboconnect.nurseify.utils.Utils;


public class BrowseFFragment extends Fragment {
    String id;
    public FragmentBrowseFBinding binding;
    View view;
    private TabAdapter adapter;

    public BrowseFFragment() {
    }

    public BrowseFFragment(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_f, null, false);

        getTabList();


        binding.imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NotificationActivity.class));
                Utils.onClickEvent(v);
            }
        });
        binding.textNurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_tab(0);
                Utils.onClickEvent(v);
            }
        });
        binding.textOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_tab(1);
                Utils.onClickEvent(v);
            }
        });
        binding.textActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_tab(2);
                Utils.onClickEvent(v);
            }
        });
        binding.textPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_tab(3);
                Utils.onClickEvent(v);
            }
        });

        return view = binding.getRoot();
    }

    private void change_tab(int i) {
//        if (!TextUtils.isEmpty(binding.editTextSearch.getText().toString())) {
        binding.editTextSearch.setText("");
        binding.editTextSearch.clearFocus();
//        }
        switch (i) {
            case 0:
//                binding.filter.setVisibility(View.VISIBLE);
                binding.textNurses.setTextColor(Color.parseColor("#8A4999"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setTextColor(Color.parseColor("#000000"));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.viewPager.setCurrentItem(0);
                break;
            case 1:
//                binding.filter.setVisibility(View.GONE);
                binding.textOffered.setTextColor(Color.parseColor("#8A4999"));
                binding.textNurses.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.viewPager.setCurrentItem(1);
                break;
            case 2:
//                binding.filter.setVisibility(View.GONE);
                binding.textActive.setTextColor(Color.parseColor("#8A4999"));
                binding.textNurses.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.viewPager.setCurrentItem(2);
                break;
            case 3:
//                binding.filter.setVisibility(View.GONE);
                binding.textPast.setTextColor(Color.parseColor("#8A4999"));
                binding.textNurses.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_tab));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_trans));
                binding.viewPager.setCurrentItem(3);
                break;
        }
    }

    private void getTabList() {

        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new Nurse_Browse_Fragment(), "Nurse");
        adapter.addFragment(new Offered_Browse_Fragment(), "Offered");
        adapter.addFragment(new Active_Browse_Fragment(), "Active");
        adapter.addFragment(new Past_Browse_Fragment(), "Past");
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