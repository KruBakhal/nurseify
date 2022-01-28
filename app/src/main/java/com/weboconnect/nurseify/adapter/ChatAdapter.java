package com.weboconnect.nurseify.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.model.Chatlist;
import com.weboconnect.nurseify.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    Typeface MR, MRR;


    private Context mContext;
    private List<Chatlist> mChat;
    private String imageurl;
    private Long previousTs = 0L;

//    FirebaseUser fuser;

    public ChatAdapter(Activity activity, List<Chatlist> mchat, String imageurl) {
        this.mContext = activity;
        this.mChat = mchat;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            Chatlist chatlist = mChat.get(position);
//            Log.d("TAG", "onBindViewHolder: "+chatlist.getSender()+"  "+new SessionManager(mContext).get_User().getId());
            if (chatlist.getSender().equals("" + new SessionManager(mContext).get_user_register_Id())) {
                holder.tv2.setText("" + chatlist.getMessage());
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
                    String date = simpleDateFormat.format(new Date(chatlist.getTime_stamp())).toString();
                    holder.tv_time2.setText(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.lay_left.setVisibility(View.GONE);
                holder.lay_right.setVisibility(View.VISIBLE);
            } else {
                holder.tv1.setText("" + chatlist.getMessage());
                holder.lay_right.setVisibility(View.GONE);
                holder.lay_left.setVisibility(View.VISIBLE);
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
                    String date = simpleDateFormat.format(new Date(chatlist.getTime_stamp())).toString();
                    holder.tv_time1.setText(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd,MMMM yyyy");
            holder.tv_time_title.setText(simpleDateFormat1.format(new Date(chatlist.getTime_stamp())));
            if (position > 0) {
                if (chatlist.getTime_stamp().equals(mChat.get(position - 1).getTime_stamp())) {
                    holder.tv_time_title.setVisibility(View.GONE);
                } else {
                    holder.tv_time_title.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tv_time_title.setVisibility(View.VISIBLE);
            }

           /* try {
                if (position > 0) {
                    Chatlist pm = mChat.get(position - 1);
                    previousTs = pm.getTime_stamp();
                }
                setTimeTextVisibility(chatlist.getTime_stamp(), previousTs, holder.tv_time_title);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.startActivity(new Intent(activity, JobDetailsActivity.class));
                }
            });
        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());

        }
    }

    private void setTimeTextVisibility(long ts1, long ts2, TextView timeText) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd,MMMM yyyy");

        if (ts2 == 0) {
            timeText.setVisibility(View.VISIBLE);
            String date = simpleDateFormat1.format(new Date(ts1)).toString();
            timeText.setText(date);
//            timeText.setText(Utils.formatDayTimeHtml(ts1));
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTimeInMillis(ts1);
            cal2.setTimeInMillis(ts2);

            boolean sameMonth = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

            if (sameMonth) {
                timeText.setVisibility(View.GONE);
                timeText.setText("");
            } else {
                timeText.setVisibility(View.VISIBLE);
                String date = simpleDateFormat1.format(new Date(ts1)).toString();
                timeText.setText(date);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mChat != null && mChat.size() != 0)
            return mChat.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView tv2, tv1, tv_time1, tv_time_title, tv_time2;
        View lay_left, lay_right;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            tv2 = itemView.findViewById(R.id.tv2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv_time1 = itemView.findViewById(R.id.tv_time1);
            tv_time2 = itemView.findViewById(R.id.tv_time2);
            lay_left = itemView.findViewById(R.id.lay_left);
            lay_right = itemView.findViewById(R.id.lay_right);
            tv_time_title = itemView.findViewById(R.id.tv_time_title);

        }
    }
}
