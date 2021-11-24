package com.weboconnect.nurseify.screen.nurse.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.CertificationCallback;
import com.weboconnect.nurseify.screen.nurse.ActiveJobDetailsActivity;
import com.weboconnect.nurseify.screen.nurse.model.NurseProfileModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CertificationsAdapter extends RecyclerView.Adapter<CertificationsAdapter.ViewHolder> {

    Activity activity;
    List<NurseProfileModel.Certitficate> list;
    CertificationCallback callback;

    public CertificationsAdapter(Activity activity, List<NurseProfileModel.Certitficate> list,CertificationCallback callback) {
        this.activity = activity;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certifications,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            int pp = position;

            holder.tv_name.setText(list.get(pp).getSearchForCredentialDefinition());
            holder.tv_date.setText(list.get(pp).getEffectiveDate());
            holder.tv_eDate.setText(list.get(pp).getExpirationDate());

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onRemove(list.get(pp).getCertificateImage(),list.get(pp).getCertificateId());
                }
            });

            Glide.with(holder.imageView.getContext()).load(list.get(pp).getCertificateImage()).into(holder.imageView);

        }catch (Exception e){
            Log.e("CertificationsAdapter ",e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView delete;

        TextView tv_name;
        TextView tv_date;
        TextView tv_eDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            imageView = itemView.findViewById(R.id.imageView);
            delete = itemView.findViewById(R.id.delete);
            tv_name = itemView.findViewById(R.id.tvSearchForCredential);
            tv_date = itemView.findViewById(R.id.tvEffectiveDate);
            tv_eDate = itemView.findViewById(R.id.tvExpirationDate);


        }
    }
}
