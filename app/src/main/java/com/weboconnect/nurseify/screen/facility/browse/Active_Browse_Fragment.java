package com.weboconnect.nurseify.screen.facility.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ActiveFAdapter;
import com.weboconnect.nurseify.adapter.TabAdapter;
import com.weboconnect.nurseify.databinding.FragmentBrowseFBinding;
import com.weboconnect.nurseify.databinding.FragmentNurseBinding;


public class Active_Browse_Fragment extends Fragment {
    String id;
    FragmentNurseBinding binding;
    View view;
    private TabAdapter adapter;

    public Active_Browse_Fragment() {
    }

    public Active_Browse_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);
        binding.recyclerView.setAdapter(new ActiveFAdapter(getActivity()));

        return view = binding.getRoot();
    }

}