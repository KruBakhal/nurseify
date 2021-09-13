package com.weboconnect.nursify.screen.nurse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nursify.R;
import com.weboconnect.nursify.adapter.ActiveAdapter;
import com.weboconnect.nursify.adapter.CompletedAdapter;
import com.weboconnect.nursify.adapter.JobAdapter;
import com.weboconnect.nursify.adapter.MessageAdapter;
import com.weboconnect.nursify.adapter.OfferedAdapter;
import com.weboconnect.nursify.databinding.FragmentMessageBinding;
import com.weboconnect.nursify.databinding.FragmentMyJobsBinding;

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
        binding.recyclerViewJobs.setAdapter(new MessageAdapter(getActivity()));
        return view = binding.getRoot();
    }
}