package com.weboconnect.nurseify.adapter;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Activity activity;
    public NotificationAdapter(Activity activity) {
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg1 = "Applications for the recording and management of a patient’s";
        String msg2 = "  #vital signs.";
        SpannableStringBuilder SS = new SpannableStringBuilder(msg1+msg2);
        SS.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.secondary_till)), msg1.length(), msg1.length()+msg2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.messageText.setText(SS);
        try {
            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.startActivity(new Intent(activity, MessageActivity.class));
                }
            });
        }catch (Exception e){
            Log.e("Service_Adapter",e.toString());
        }
    }
    @Override
    public int getItemCount() {
        return 6;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView messageText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            messageText = itemView.findViewById(R.id.messageText);
        }
    }
}
