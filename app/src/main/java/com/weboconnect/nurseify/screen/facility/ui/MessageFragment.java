package com.weboconnect.nurseify.screen.facility.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.MessageAdapter;
import com.weboconnect.nurseify.databinding.FragmentMessageBinding;

public class MessageFragment extends Fragment {
    String id;
    FragmentMessageBinding binding;
    View view;
    public MessageFragment(){ }
    public MessageFragment(String id) {
        this.id = id;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_message, null, false);
        binding.recyclerViewJobs.setAdapter(new MessageAdapter(getActivity(),false));
        return view = binding.getRoot();
    }
}