package com.weboconnect.nursify.screen.nurse.ui;

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

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.adapter.FacilityAdapter;
import com.weboconnect.nursify.adapter.JobAdapter;
import com.weboconnect.nursify.databinding.FragmentBrowseBinding;


public class BrowseFragment extends Fragment {
    String id;
    FragmentBrowseBinding binding;
    View view;
    public BrowseFragment(){ }
    public BrowseFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_browse, null, false);
        binding.recyclerViewJobs.setAdapter(new JobAdapter(getActivity()));
        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View loc = getLayoutInflater().from(getContext()).inflate(R.layout.dialog_filter, null);
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
        binding.textJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imgFilter.setVisibility(View.VISIBLE);
                binding.editTextSearch.setHint("Search for jobs...");
                binding.textJobs.setTextColor(Color.parseColor("#8A4999"));
                binding.textFacilities.setTextColor(Color.parseColor("#000000"));
                binding.textJobs.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_tab));
                binding.textFacilities.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(new JobAdapter(getActivity()));
            }
        });
        binding.textFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imgFilter.setVisibility(View.GONE);
                binding.editTextSearch.setHint("Search for Facilities");
                binding.textFacilities.setTextColor(Color.parseColor("#8A4999"));
                binding.textJobs.setTextColor(Color.parseColor("#000000"));
                binding.textFacilities.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.btn_tab));
                binding.textJobs.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.bg_trans));
                binding.recyclerViewJobs.setAdapter(new FacilityAdapter(getActivity()));
            }
        });
        return view = binding.getRoot();
    }
}