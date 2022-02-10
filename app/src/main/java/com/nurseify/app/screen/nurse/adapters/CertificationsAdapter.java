package com.nurseify.app.screen.nurse.adapters;

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
import com.nurseify.app.intermediate.CertificationCallback;
import com.nurseify.app.screen.nurse.model.UserProfileData;
import com.nurseify.app.utils.Utils;

import java.util.List;

public class CertificationsAdapter extends RecyclerView.Adapter<CertificationsAdapter.ViewHolder> {

    Activity activity;
    List<UserProfileData.Certitficate> list;
    CertificationCallback callback;

    public CertificationsAdapter(Activity activity, List<UserProfileData.Certitficate> list, CertificationCallback callback) {
        this.activity = activity;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_certifications, parent, false);
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
            holder.lay_Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onRemove(position, list.get(position));
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    callback.onEdit(position);
                }
            });


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
        View edit, lay_Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            lay_Delete = itemView.findViewById(R.id.layDelete);
            imageView = itemView.findViewById(R.id.imageView);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            tv_name = itemView.findViewById(R.id.tv_search_credential);
            tv_date = itemView.findViewById(R.id.tvEffectiveDate);
            tv_eDate = itemView.findViewById(R.id.tvExpirationDate);


        }
    }
}
