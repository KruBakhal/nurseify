package com.weboconnect.nurseify.screen.facility.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.NursesAdapter;
import com.weboconnect.nurseify.adapter.PastAdapter;
import com.weboconnect.nurseify.adapter.TabAdapter;
import com.weboconnect.nurseify.databinding.FragmentBrowseFBinding;
import com.weboconnect.nurseify.databinding.FragmentNurseBinding;


public class Past_Browse_Fragment extends Fragment {
    String id;
    FragmentNurseBinding binding;
    View view;
    private TabAdapter adapter;

    public Past_Browse_Fragment() {
    }

    public Past_Browse_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);

        binding.recyclerView.setAdapter(new PastAdapter(getActivity()));
        return view = binding.getRoot();
    }

}