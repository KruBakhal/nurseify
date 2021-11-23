package com.weboconnect.nurseify.screen.facility.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveFAdapter;
import com.weboconnect.nurseify.screen.nurse.adapters.JobAdapter;
import com.weboconnect.nurseify.adapter.NursesAdapter;
import com.weboconnect.nurseify.adapter.OfferedFAdapter;
import com.weboconnect.nurseify.adapter.PastAdapter;
import com.weboconnect.nurseify.databinding.FragmentBrowseFBinding;


public class BrowseFFragment extends Fragment {
    String id;
    FragmentBrowseFBinding binding;
    View view;
    public BrowseFFragment(){ }
    public BrowseFFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_browse_f, null, false);
        binding.recyclerViewJobs.setAdapter(new JobAdapter(getActivity()));
        binding.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View loc = getLayoutInflater().from(getContext()).inflate(R.layout.dialog_filter_f, null);
                final Dialog dialog = new Dialog(getContext(),R.style.AlertDialog);
                dialog.setContentView(loc);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.setCancelable(true);
                dialog.show();
                ImageView close = dialog.findViewById(R.id.close_dialog);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                TextView text_apply = dialog.findViewById(R.id.text_apply);
                text_apply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        binding.textNurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.filter.setVisibility(View.VISIBLE);
                binding.textNurses.setTextColor(Color.parseColor("#8A4999"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setTextColor(Color.parseColor("#000000"));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_tab));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(new NursesAdapter(getActivity()));
            }
        });
        binding.textOffered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.filter.setVisibility(View.GONE);
                binding.textOffered.setTextColor(Color.parseColor("#8A4999"));
                binding.textNurses.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_tab));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(new OfferedFAdapter(getActivity()));
            }
        });
        binding.textActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.filter.setVisibility(View.GONE);
                binding.textActive.setTextColor(Color.parseColor("#8A4999"));
                binding.textNurses.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_tab));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(new ActiveFAdapter(getActivity()));
            }
        });
        binding.textPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.filter.setVisibility(View.GONE);
                binding.textPast.setTextColor(Color.parseColor("#8A4999"));
                binding.textNurses.setTextColor(Color.parseColor("#000000"));
                binding.textActive.setTextColor(Color.parseColor("#000000"));
                binding.textOffered.setTextColor(Color.parseColor("#000000"));
                binding.textPast.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_tab));
                binding.textNurses.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textActive.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.textOffered.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(new PastAdapter(getActivity()));
            }
        });
        return view = binding.getRoot();
    }
}