package com.weboconnect.nurseify.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.intermediate.OnItemClick;
import com.weboconnect.nurseify.screen.nurse.MessageActivity;
import com.weboconnect.nurseify.screen.nurse.model.Chatlist;
import com.weboconnect.nurseify.screen.nurse.model.User;
import com.weboconnect.nurseify.utils.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    boolean isNurse;
    private Context mContext;
    private List<User> mUsers;
    private boolean ischat;
    private OnItemClick onItemClick;
    String theLastMessage = "default";

    public MessageAdapter(Activity activity, boolean isNurse) {
        this.isNurse = isNurse;
        this.mContext = activity;
    }


    public MessageAdapter(Context context, OnItemClick onItemClick, ArrayList<User> mUsers, boolean b) {
        this.mContext = context;
        this.mUsers = mUsers;
        this.ischat = b;
        isNurse = true;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isNurse) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_f, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            User user = mUsers.get(position);
            holder.tv_name.setText("" + user.getFull_name());
            holder.tv_title.setText("" + user.getSpecialty());
            if (TextUtils.isEmpty(user.getProfile_path())) {
                holder.circleImageView.setImageResource(R.drawable.person);
            } else {
                Glide.with(mContext).load(user.getProfile_path()).error(R.drawable.person).into(holder.circleImageView);
            }
            if (user.getStatus()) {
                holder.img_status.setImageResource(R.color.drak_green);
            } else {
                holder.img_status.setImageResource(R.color.drak_yellow);
            }
            unreadMessageCount(user.getId(), holder.tv_count, holder.lay_count);
            lastStatus(user.getId(), holder.img_status);
            lastMessage(user.getId(), holder.tv_last_msg, holder.tv_last_msg_time);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, MessageActivity.class)
                            .putExtra("sender_id", user.getId()));
                }
            });

        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());
        }
    }

    private void unreadMessageCount(String id, TextView tv_count, View lay_count) {
        String user_id = new SessionManager(mContext).get_User().getId();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int unread = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chat = snapshot.getValue(Chatlist.class);
                    if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(user_id)&& !TextUtils.isEmpty(chat.getReceiver())&& !TextUtils.isEmpty(chat.getSender())
                            && chat.getSender().equals(id) && chat.getReceiver().equals(user_id) && chat.getIs_seen() == 0) {
                        unread++;
                    }
                }
                if (unread != 0) {
                    tv_count.setText("" + unread);
                    lay_count.setVisibility(View.VISIBLE);
                } else {
                    lay_count.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void lastStatus(String id, ImageView img_status) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        User chat = snapshot.getValue(User.class);
                        if ((chat.getId().equals(id))) {
                            if (chat.getStatus()) {
                                img_status.setImageResource(R.color.drak_green);
                            } else {
                                img_status.setImageResource(R.color.drak_yellow);
                            }
                        }
                    } catch (Exception e) {
                        Log.d("TAG", "onDataChange: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void lastMessage(final String userid, TextView tv_last_msg, final TextView last_msg_time) {

        String firebaseUser = new SessionManager(mContext).get_User().getId();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chat = snapshot.getValue(Chatlist.class);
                    if (firebaseUser != null && chat != null) {
                        if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(firebaseUser)&& !TextUtils.isEmpty(chat.getSender()) && !TextUtils.isEmpty(chat.getReceiver())&&
                                (chat.getReceiver().equals(firebaseUser) && chat.getSender().equals(userid)) ||
                                (chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser))) {
                            theLastMessage = chat.getMessage();
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                                String date = simpleDateFormat.format(new Date(chat.getTime_stamp())).toString();
                                last_msg_time.setText(date);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                switch (theLastMessage) {
                    case "default":
                        tv_last_msg.setText("No Message");
                        break;

                    default:
                        tv_last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        if (mUsers != null && mUsers.size() != 0)
            return mUsers.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        TextView tv_name, tv_title, tv_last_msg, tv_last_msg_time, tv_count;
        ImageView img_status, circleImageView;
        View lay_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            img_status = itemView.findViewById(R.id.img_status);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_last_msg = itemView.findViewById(R.id.tv_last_msg);
            tv_last_msg_time = itemView.findViewById(R.id.tv_last_msg_time);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            lay_count = itemView.findViewById(R.id.lay_count);
            tv_count = itemView.findViewById(R.id.tv_count);
        }
    }
}
