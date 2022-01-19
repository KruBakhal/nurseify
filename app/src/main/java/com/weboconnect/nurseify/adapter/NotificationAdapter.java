package com.weboconnect.nurseify.adapter;

import android.app.Activity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.ItemCallback;
import com.weboconnect.nurseify.screen.nurse.model.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final boolean isCall;
    Activity activity;
    List<NotificationModel.Notification> list;

    public NotificationAdapter(Activity activity, List<NotificationModel.Notification> list, boolean b, ItemCallback itemCallback) {
        this.activity = activity;
        this.list = list;
        this.isCall = b;
        this.itemCallback = itemCallback;
    }

    ItemCallback itemCallback;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String msg1 = "Applications for the recording and management of a patient’s";
        String msg2 = "  #vital signs.";
        SpannableStringBuilder SS = new SpannableStringBuilder(msg1 + msg2);
        SS.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.secondary_till)), msg1.length(), msg1.length() + msg2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        holder.messageText.setText(SS);
        try {

            int pp = position;

            holder.messageText.setText(Html.fromHtml(list.get(pp).getMessage()));
            holder.tv_date.setText(list.get(pp).getDate());
            if (isCall) {
                holder.close.setVisibility(View.VISIBLE);
            } else {
                holder.close.setVisibility(View.GONE);
            }
            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCallback.onClick(position);
                }
            });
        } catch (Exception e) {
            Log.e("NotificationAdapter", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView messageText;
        TextView tv_date;
        ImageView close;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            messageText = itemView.findViewById(R.id.messageText);
            tv_date = itemView.findViewById(R.id.tv_date);
            close = itemView.findViewById(R.id.imgClose);
        }
    }
}
