package com.nurseify.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nurseify.app.R;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.utils.Utils;

import java.util.List;

public class Nurse_CertificationsAdapter extends RecyclerView.Adapter<Nurse_CertificationsAdapter.ViewHolder> {

    Activity activity;
    List<UserProfileData.Certitficate> list;

    public Nurse_CertificationsAdapter(Activity activity, List<UserProfileData.Certitficate> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credential, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {

            int pp = position;

            holder.tv_name.setText(list.get(pp).getSearchForCredentialDefinition());
            holder.tv_date.setText(list.get(pp).getEffectiveDate());
            holder.tv_eDate.setText(list.get(pp).getExpirationDate());


            if (!TextUtils.isEmpty(list.get(pp).getCertificateImage())
                    && !TextUtils.isEmpty(list.get(pp).getCertificateImage_base())) {
                byte[] decodeString = Utils.get_base_images(list.get(pp).getCertificateImage_base());
                Glide.with(holder.imageView.getContext()).load(decodeString)
                        .placeholder(R.drawable.place_holder_img)
                        .error(R.drawable.place_holder_img).into(holder.imageView);
            }


        } catch (Exception e) {
            Log.e("CertificationsAdapter ", e.toString());

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


            imageView = itemView.findViewById(R.id.imgCertificate);
            delete = itemView.findViewById(R.id.delete);
            tv_name = itemView.findViewById(R.id.tv_search_credential);
            tv_date = itemView.findViewById(R.id.tvEffectiveDate);
            tv_eDate = itemView.findViewById(R.id.tvExpirationDate);


        }
    }
}
