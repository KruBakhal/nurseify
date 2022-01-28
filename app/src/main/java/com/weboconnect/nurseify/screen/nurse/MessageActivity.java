package com.weboconnect.nurseify.screen.nurse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ChatAdapter;
import com.weboconnect.nurseify.databinding.ActivityMessageBinding;
import com.weboconnect.nurseify.screen.nurse.model.Chatlist;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;
import com.weboconnect.nurseify.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    private String receiver_id;
    private DatabaseReference reference;
    private String sender_id;

    private List<Chatlist> mchat = new ArrayList<>();
    private ChatAdapter chatAdapter;
    private String both_user_key;
    private ValueEventListener messeageRefrence;
    private ValueEventListener lastStatusReference;
    private SessionManager sessionManager;
    private String receiver_profile_path;
    private String receiver_full_name;
    private String receiver_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MessageActivity.this, R.layout.activity_message);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sessionManager = new SessionManager(this);
        sender_id = new SessionManager(MessageActivity.this).get_user_register_Id();
        receiver_id = getIntent().getStringExtra("receiver_id");
        receiver_profile_path = getIntent().getStringExtra(Constant.PROFILE_PATH);
        receiver_full_name = getIntent().getStringExtra(Constant.FULL_NAME);
        receiver_email = getIntent().getStringExtra(Constant.EMAIL_ID);
        binding.tvTitle.setText("" + receiver_full_name);
        Glide.with(this).load(receiver_profile_path)
                .placeholder(R.drawable.person).error(R.drawable.person).into(binding.imgProfile);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notify = true;
                String msg = binding.textSend.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                binding.textSend.setText("");
            }
        });

        both_user_key = Utils.getCombine_Node_Key(sessionManager.get_TYPE(),
                sessionManager.get_user_register_Id(), receiver_id);

        reference = FirebaseDatabase.getInstance().getReference(Constant.CHAT_NODE).child(both_user_key);

        messeageRefrence = reference.orderByChild(Constant.TIME_STAMP)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mchat.clear();
                        if (dataSnapshot.getChildren() != null && dataSnapshot.hasChildren())
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Chatlist chat = snapshot.getValue(Chatlist.class);
                                mchat.add(chat);
                                chatAdapter = new ChatAdapter(MessageActivity.this, mchat,
                                        null);
                                binding.recyclerViewJobs.setAdapter(chatAdapter);
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        seenMessage();

    }


    private void seenMessage() {
        reference = FirebaseDatabase.getInstance().getReference(Constant.CHAT_NODE)
                .child(both_user_key);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chat = snapshot.getValue(Chatlist.class);
                    if (chat.getReceiver().equals(sender_id)) {
                        snapshot.getRef().child(Constant.IS_SEEN).setValue(1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender_id);
        hashMap.put("receiver", receiver_id);
        hashMap.put("message", message);
        hashMap.put("timestamp", System.currentTimeMillis());
        hashMap.put("is_seen", 0);
        hashMap.put("type", "text");

        reference.child(Constant.CHAT_NODE).child(both_user_key).push().setValue(hashMap);

        HashMap<String, Object> user_hashMap = get_User_Hash();
        HashMap<String, Object> mech_hashMap = get_Mech_Hash();

        reference.child(Constant.USER_NODE).child(sender_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot == null || snapshot.getValue() == null) {
                            // users nodes update profile
                            reference.child(Constant.USER_NODE).child(sender_id)
                                    .updateChildren(user_hashMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        reference.child(Constant.USER_NODE).child(receiver_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot == null || snapshot.getValue() == null) {
                            // users nodes update profile
                            reference.child(Constant.USER_NODE).child(receiver_id)
                                    .updateChildren(mech_hashMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        reference = FirebaseDatabase.getInstance().getReference();
        String send, receive;

        //sender user node
        reference.child(Constant.USER_NODE)
                .child(sender_id).child(Constant.CHAT_USERS_CHILD)
                .child(receiver_id)
                .updateChildren(hashMap);

        // receiver user node
        reference.child(Constant.USER_NODE)
                .child(receiver_id).child(Constant.CHAT_USERS_CHILD)
                .child(sender_id)
                .updateChildren(hashMap);
    }

    private HashMap<String, Object> get_User_Hash() {
        String userData = sessionManager.get_TYPE();
        String email, full_name, id, profile, specialty, status, type;
        if (userData.equals(Constant.CONST_FACULTY_TYPE)) {
            email = sessionManager.get_facilityProfile().getFacilityEmail();
            profile = sessionManager.get_facilityProfile().getFacilityLogo();
            full_name = sessionManager.get_facilityProfile().getFacilityName();
            id = sessionManager.get_facilityProfile().getUserId();
            specialty = " ";
            status = "true";
            type = "2";
        } else {
            email = sessionManager.get_User().getEmail();
            profile = sessionManager.get_User().getImage();
            full_name = sessionManager.get_User().getFullName();
            id = sessionManager.get_User().getId();
            specialty = sessionManager.get_User().getSpecialty().get(0).getName();
            status = "true";
            type = "1";
        }
        if (TextUtils.isEmpty(specialty))
            specialty = " ";
        HashMap<String, Object> user_hashMap = new HashMap<>();
        user_hashMap.put(Constant.EMAIL_ID, email);
        user_hashMap.put(Constant.ID, id);
        user_hashMap.put(Constant.FULL_NAME, full_name);
        user_hashMap.put(Constant.PROFILE_PATH, profile);
        user_hashMap.put(Constant.ONLINE_STATUS, true);
        user_hashMap.put(Constant.SPECIALITY, specialty);
        user_hashMap.put(Constant.TYPE, type);
        return user_hashMap;

    }

    private HashMap<String, Object> get_Mech_Hash() {
        String userData = sessionManager.get_TYPE();
        String email, full_name, id, profile, specialty, status, type;
        email = receiver_email;
        profile = receiver_profile_path;
        full_name = receiver_full_name;
        specialty = " ";
        id = receiver_id;
        status = "false";
        type = "2";
        HashMap<String, Object> mech_hashMap = new HashMap<>();
        mech_hashMap.put(Constant.EMAIL_ID, email);
        mech_hashMap.put(Constant.ID, id);
        mech_hashMap.put(Constant.FULL_NAME, full_name);
        mech_hashMap.put(Constant.PROFILE_PATH, profile);
        mech_hashMap.put(Constant.ONLINE_STATUS, true);
        mech_hashMap.put(Constant.TYPE, type);
        return mech_hashMap;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        update_user_status(true);
    }

    private void update_user_status(boolean status) {
        /*try {
            HashMap<String, Object> sdsd = new HashMap<>();
            sdsd.put("status", status);
            String userid = new SessionManager(HomeActivity.this).get_User().getId();
            FirebaseDatabase.getInstance().getReference("users")
                    .child(userid).child("status").setValue(status);
        } catch (Exception e) {
            Log.d("TAG", "update_user_status: " + e.getMessage());
        }*/

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child(Constant.USER_NODE)
                .child(new SessionManager(MessageActivity.this).get_user_register_Id());

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() == null) {
//                        create_user_inside_firebase(new SessionManager(getContext()).get_User());
                    } else {
                        try {
                            HashMap<String, Object> sdsd = new HashMap<>();
                            sdsd.put("status", status);
                            String userid = new SessionManager(MessageActivity.this).get_user_register_Id();
                            FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                                    .child(userid).child(Constant.ONLINE_STATUS)
                                    .setValue(status);
                        } catch (Exception e) {
                            Log.d("TAG", "update_user_status: " + e.getMessage());
                        }
                    }
                } catch (Exception exception) {
                    Log.d("TAG_check_User_exist", "onDataChange: " + exception.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        update_user_status(false);
    }
}