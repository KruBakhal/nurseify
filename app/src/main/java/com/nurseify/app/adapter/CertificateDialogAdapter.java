package com.nurseify.app.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurseify.app.R;
import com.nurseify.app.intermediate.CertificationCallback;
import com.nurseify.app.screen.nurse.model.AddCredentialData;
import com.nurseify.app.screen.nurse.model.UserProfileData;

import java.util.Collections;
import java.util.List;

public class CertificateDialogAdapter extends RecyclerView.Adapter<CertificateDialogAdapter.ViewHolder> {

    Activity activity;
    List<AddCredentialData> list;
    List<UserProfileData.Certitficate> certitficate;
    CertificationCallback callback;
    int type = 1;

    public CertificateDialogAdapter(Activity activity, List<AddCredentialData> list
    ) {
        this.activity = activity;
        Collections.reverse(list);
        this.list = list;

    }

    public CertificateDialogAdapter(Activity activity, int type, List<UserProfileData.Certitficate> certitficate, CertificationCallback callback) {
        this.activity = activity;
        this.certitficate = certitficate;
        this.callback = callback;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certifications_dialog, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (type == 1) {
                int pp = position;
                holder.tv_name.setText(list.get(pp).getSearchForCredentialDefinition());

            } else {


            }


        } catch (Exception e) {
            Log.e("CertificationsAdapter ", e.toString());

        }
    }

    @Override
    public int getItemCount() {
        if (this.list == null || this.list.size() == 0) {
            return 0;
        }
        return this.list.size();
    }

    public void setList(List<AddCredentialData> list_certificate) {
       /* Collections.reverse(list_certificate);
        if (this.list != null && list.size() != 0) {
            this.list.clear();
        }
        this.list.addAll(list_certificate);*/
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_name = itemView.findViewById(R.id.tv_name);


        }
    }
}
