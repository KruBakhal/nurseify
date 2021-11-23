package com.weboconnect.nurseify.screen.nurse.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
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
import com.weboconnect.nurseify.intermediate.FacilityListCallback;
import com.weboconnect.nurseify.screen.nurse.model.FacilityModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

    Activity activity;
    List<FacilityModel.Facility> list;
    FacilityListCallback callback;

    public FacilityAdapter(Activity activity, List<FacilityModel.Facility> list, FacilityListCallback callback) {
        this.activity = activity;
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            
            Glide.with(holder.itemView.getContext()).load(list.get(position).getFacilityLogo()).into(holder.imageView);

            holder.tv_name.setText(list.get(position).getName());
            holder.tv_description.setText(Html.fromHtml(list.get(position).getAboutFacility()));
            holder.tv_job.setText(list.get(position).getTotalJobs()+" Job");
            holder.tv_rating.setText((""+list.get(position).getRating()));
            holder.tv_address.setText(list.get(position).getPostcode()+" "+list.get(position).getAddress());

            String id = list.get(position).getId();


            if (list.get(position).getIsFollow()){
                holder.tv_follow.setBackground(activity.getResources().getDrawable(R.drawable.btn_back));
                holder.tv_follow.setTextColor(activity.getResources().getColor(R.color.white));
            }else {
                holder.tv_follow.setBackground(activity.getResources().getDrawable(R.drawable.btn_back_gray));
                holder.tv_follow.setTextColor(activity.getResources().getColor(R.color.black));
            }

            if (list.get(position).getIsLike()){
                holder.img_heart.setColorFilter(Color.parseColor("#ED2323"));
            }else {
                holder.img_heart.setColorFilter(Color.parseColor("#000000"));
            }


            holder.tv_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!list.get(position).getIsFollow()){
                        holder.tv_follow.setBackground(activity.getResources().getDrawable(R.drawable.btn_back));
                        holder.tv_follow.setTextColor(activity.getResources().getColor(R.color.white));
                        callback.onFollow(id,list.get(position).getType().toString());
                    }
                }
            });

            holder.img_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position).getIsFollow()){
                        callback.onLike(id,"0");
                        holder.img_heart.setColorFilter(Color.parseColor("#000000"));
                    }
                    else {
                        callback.onLike(id,"1");
                        holder.img_heart.setColorFilter(Color.parseColor("#ED2323"));
                    }
                }
            });



            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.startActivity(new Intent(activity, JobDetailsActivity.class));
                }
            });

        }catch (Exception e){
            Log.e("Service_Adapter",e.toString());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mainLayout;

        CircleImageView imageView;
        TextView tv_name;
        TextView tv_address;
        TextView tv_job;
        TextView tv_rating;
        TextView tv_description;

        ImageView img_heart;
        ImageView img_share;
        TextView tv_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            imageView = itemView.findViewById(R.id.image);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_job = itemView.findViewById(R.id.tv_job);
            tv_description = itemView.findViewById(R.id.tv_description);
            img_heart = itemView.findViewById(R.id.img_heart);
            tv_follow = itemView.findViewById(R.id.tv_follow);

        }
    }

}
