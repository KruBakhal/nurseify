package com.weboconnect.nurseify.screen.facility.my_jobs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.PastAdapter;
import com.weboconnect.nurseify.adapter.TabAdapter;
import com.weboconnect.nurseify.databinding.FragmentNurseBinding;
import com.weboconnect.nurseify.intermediate.OfferedJobCallback;
import com.weboconnect.nurseify.screen.nurse.adapters.CompletedAdapter;
import com.weboconnect.nurseify.screen.nurse.model.CompletedJobModel;

import java.util.List;


public class Complete_Jobs_Fragment extends Fragment {
    String id;
    FragmentNurseBinding binding;
    View view;
    private TabAdapter adapter;
    private int list_Completed_Job;
    private List<CompletedJobModel.CompletedDatum> offeredJobCallback;

    public Complete_Jobs_Fragment() {
    }

    public Complete_Jobs_Fragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nurse, null, false);

        binding.recyclerView.setAdapter(new CompletedAdapter(getActivity(), list_Completed_Job, offeredJobCallback,
                new OfferedJobCallback() {
                    @Override
                    public void onAccept(String jobId) {

                    }

                    @Override
                    public void onReject(String jobId) {

                    }

                    @Override
                    public void onClick(int pos) {

                    }
                }));
        return view = binding.getRoot();
    }

}