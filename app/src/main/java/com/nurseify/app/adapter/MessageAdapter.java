package com.nurseify.app.adapter;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nurseify.app.R;
import com.nurseify.app.intermediate.OnItemClick;
import com.nurseify.app.screen.facility.MessageFacilityActivity;
import com.nurseify.app.screen.facility.model.NurseDatum;
import com.nurseify.app.screen.nurse.MessageActivity;
import com.nurseify.app.screen.nurse.model.Chatlist;
import com.nurseify.app.screen.nurse.model.User;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;
import com.nurseify.app.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    boolean isNurse;
    private Context mContext;
    private List<User> mUsers;
    private OnItemClick onItemClick;
    String theLastMessage = "default";

    public MessageAdapter(Activity activity, boolean isNurse) {
        this.isNurse = isNurse;
        this.mContext = activity;
    }


    public MessageAdapter(Context context, OnItemClick onItemClick, ArrayList<User> mUsers, boolean b) {
        this.mContext = context;
        this.mUsers = mUsers;
        isNurse = b;
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
            holder.tv_title.setText("" + (TextUtils.isEmpty(user.getSpecialty()) ? "" : user.getSpecialty()));
            try {
                if (user.getChat_model() != null) {
                    holder.tv_last_msg.setText("" + user.getChat_model().getMessage());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                    String date = simpleDateFormat.format(new Date(user.getChat_model().getTime_stamp())).toString();
                    holder.tv_last_msg_time.setText(date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(user.getProfile_path())) {
                holder.circleImageView.setImageResource(R.drawable.person);
            } else {
                Glide.with(mContext).load(user.getProfile_path())
                        .placeholder(R.drawable.person).error(R.drawable.person)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        .into(holder.circleImageView);
            }
            if (user.getStatus()) {
                holder.img_status.setImageResource(R.color.drak_green);
            } else {
                holder.img_status.setImageResource(R.color.drak_yellow);
            }
            unreadMessageCount(user.getId(), user.getType(), holder.tv_count, holder.lay_count);
            lastStatus(user.getId(), holder.img_status);
            // lastMessage(user.getId(), holder.tv_last_msg, holder.tv_last_msg_time);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNurse) {
                        mContext.startActivity(new Intent(mContext, MessageActivity.class)
                                .putExtra("receiver_id", user.getId())
                                .putExtra(Constant.PROFILE_PATH, user.getProfile_path())
                                .putExtra(Constant.FULL_NAME, user.getFull_name())
                                .putExtra(Constant.EMAIL_ID, user.getEmail())
                        );
                    } else {
                        NurseDatum datum = new NurseDatum();
                        datum.setUserId(user.getId());
                        datum.setNurseEmail(user.getEmail());
                        datum.setFirstName(user.getFull_name());
                        datum.setSpecialty(user.getSpecialty());
                        datum.setNurseLogo(user.getProfile_path());
                        mContext.startActivity(new Intent(mContext, MessageFacilityActivity.class)
                                .putExtra("receiver_id", user.getId())
                                .putExtra(Constant.STR_RESPONSE_DATA, new Gson().toJson(datum))
                        );
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());
        }
    }

    private void unreadMessageCount(String n_id, String type, TextView tv_count, View lay_count) {
        String account_id = new SessionManager(mContext).get_user_register_Id();
        String both_user_key;
        if (new SessionManager(mContext).get_TYPE().equals(Constant.CONST_FACULTY_TYPE)) {
            both_user_key = Utils.getCombine_Node_Key(new SessionManager(mContext).get_TYPE(), n_id, account_id);
        } else
            both_user_key = Utils.getCombine_Node_Key(new SessionManager(mContext).get_TYPE(), account_id, n_id);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constant.CHAT_NODE)
                .child(both_user_key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int unread = 0;
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chatlist chat = snapshot.getValue(Chatlist.class);
                        if (chat.getReceiver().equals(account_id) &&
                                chat.getIs_seen() == 0) {
                            unread++;
                        }
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void lastStatus(String id, ImageView img_status) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                .child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null)
                    try {
                        if ((boolean) dataSnapshot.child(Constant.ONLINE_STATUS).getValue()) {
                            img_status.setImageResource(R.color.drak_green);
                        } else {
                            img_status.setImageResource(R.color.drak_yellow);
                        }
                    } catch (Exception e) {
                        Log.d("TAG", "onDataChange: " + e.getMessage());
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void lastMessage(final String userid, TextView tv_last_msg, final TextView last_msg_time) {

        String firebaseUser = new SessionManager(mContext).get_user_register_Id();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chat = snapshot.getValue(Chatlist.class);
                    if (firebaseUser != null && chat != null) {
                        if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(firebaseUser) && !TextUtils.isEmpty(chat.getSender()) && !TextUtils.isEmpty(chat.getReceiver()) &&
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
