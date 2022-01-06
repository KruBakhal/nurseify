package com.weboconnect.nurseify.screen.nurse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ChatAdapter;
import com.weboconnect.nurseify.databinding.ActivityMessageBinding;
import com.weboconnect.nurseify.screen.facility.model.FacilityProfile;
import com.weboconnect.nurseify.screen.facility.model.NurseDatum;
import com.weboconnect.nurseify.screen.nurse.model.Chatlist;
import com.weboconnect.nurseify.screen.nurse.model.User;
import com.weboconnect.nurseify.utils.Constant;
import com.weboconnect.nurseify.utils.SessionManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    private String reciever_id;
    private DatabaseReference reference;
    private String sender_id;
    private ArrayList<Chatlist> mchat;
    private ChatAdapter chatAdapter;
    private NurseDatum nurse_model;
    private FacilityProfile facility_model;

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

        sender_id = new SessionManager(MessageActivity.this).get_user_register_Id();
        String str = getIntent().getStringExtra(Constant.STR_RESPONSE_DATA);
        // status_constraint = getIntent().getBooleanExtra("from_applied", false);
        Type type = new TypeToken<NurseDatum>() {
        }.getType();
        nurse_model = new Gson().fromJson(str, type);
        facility_model = new SessionManager(MessageActivity.this).get_facilityProfile();

        Intent intent = getIntent();
        reciever_id = intent.getStringExtra("sender_id");

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notify = true;
                String msg = binding.textSend.getText().toString();
                String time = String.valueOf(System.currentTimeMillis());
                if (!msg.equals("")) {
                    sendMessage(sender_id, reciever_id, msg, time);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                binding.textSend.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("users").child(reciever_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    mchat = new ArrayList<>();
                    chatAdapter = new ChatAdapter(MessageActivity.this, mchat, null);
                    binding.recyclerViewJobs.setAdapter(chatAdapter);
                    return;
                }
                if (!TextUtils.isEmpty(user.getFull_name()))
                    binding.tvTitle.setText(user.getFull_name());
                if (TextUtils.isEmpty(user.getProfile_path())) {
                    binding.imgProfile.setImageResource(R.drawable.person);
                } else {
                    //and this
                    Glide.with(getApplicationContext()).load(user.getProfile_path())
                            .error(R.drawable.person).into(binding.imgProfile);
                }

                readMesagges(sender_id, reciever_id, user.getProfile_path());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(reciever_id, sender_id);
    }

    private void seenMessage(String chat_user_id, final String userid) {
        reference = FirebaseDatabase.getInstance().getReference("chats");
        ValueEventListener seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chat = snapshot.getValue(Chatlist.class);
                    if (chat.getReceiver().equals(userid) && chat.getSender().equals(chat_user_id)) {
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("isseen", true);
                        snapshot.getRef().child("is_seen").setValue(1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, final String receiver, String message, String time) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("time_stamp", System.currentTimeMillis());
        hashMap.put("is_seen", 0);
        hashMap.put("type", "text");
        reference.child("chats").push().setValue(hashMap);
        HashMap<String, Object> mech_hashMap = get_Mech_Hash();
        reference.child(Constant.USER_NODE).child(receiver)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot == null || snapshot.getValue() == null) {
                            // users nodes update profile
                            reference.child(Constant.USER_NODE).child(receiver)
                                    .updateChildren(mech_hashMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        HashMap<String, Object> user_hashMap = get_User_Hash(sender, receiver, message, time);
        reference.child(Constant.USER_NODE).child(sender)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot == null || snapshot.getValue() == null) {
                            // users nodes update profile
                            reference.child(Constant.USER_NODE).child(sender)
                                    .updateChildren(user_hashMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private HashMap<String, Object> get_User_Hash(String sender, String receiver, String message, String time) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", facility_model.getFacilityEmail());
        hashMap.put("id", facility_model.getUserId());
        hashMap.put("full_name", facility_model.getFacilityName());
        hashMap.put("profile_path", facility_model.getFacilityLogo());
        hashMap.put("status", true);
        hashMap.put("specialty", facility_model.getFacilityTypeDefinition());
        hashMap.put("type", 2); // facility side        return mech_hashMap;
        return hashMap;
    }

    private HashMap<String, Object> get_Mech_Hash() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", nurse_model.getNurseEmail());
        hashMap.put("id", nurse_model.getUserId());
        hashMap.put("full_name", nurse_model.getFirstName() + " " + nurse_model.getLastName());
        hashMap.put("profile_path", nurse_model.getNurseLogo());
        hashMap.put("status", false);
        hashMap.put("specialty", " ");
        hashMap.put("type", 1); // nurse side        return mech_hashMap;
        return hashMap;
    }

    private void readMesagges(final String myid, final String userid, final String imageurl) {
        mchat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chatlist chat = snapshot.getValue(Chatlist.class);
                    if ((chat.getReceiver().equals(myid) && chat.getSender().equals(userid))
                            || (chat.getReceiver().equals(userid) && chat.getSender().equals(myid))) {
                        mchat.add(chat);
                    }
                    chatAdapter = new ChatAdapter(MessageActivity.this, mchat, imageurl);
                    binding.recyclerViewJobs.setAdapter(chatAdapter);
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}