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
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.ChatAdapter;
import com.weboconnect.nurseify.databinding.ActivityMessageBinding;
import com.weboconnect.nurseify.screen.nurse.model.Chatlist;
import com.weboconnect.nurseify.screen.nurse.model.User;
import com.weboconnect.nurseify.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {

    ActivityMessageBinding binding;
    private String chat_user_id;
    private DatabaseReference reference;
    private String nurse_id;
    private ArrayList<Chatlist> mchat;
    private ChatAdapter chatAdapter;

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

        nurse_id = new SessionManager(MessageActivity.this).get_user_register_Id();

        Intent intent = getIntent();
        chat_user_id = intent.getStringExtra("sender_id");

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                notify = true;
                String msg = binding.textSend.getText().toString();
                String time = String.valueOf(System.currentTimeMillis());
                if (!msg.equals("")) {
                    sendMessage(nurse_id, chat_user_id, msg, time);
                } else {
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                binding.textSend.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("users").child(chat_user_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if(!TextUtils.isEmpty(user.getFull_name()))
                binding.tvTitle.setText(user.getFull_name());
                if (TextUtils.isEmpty(user.getProfile_path())) {
                    binding.imgProfile.setImageResource(R.drawable.person);
                } else {
                    //and this
                    Glide.with(getApplicationContext()).load(user.getProfile_path())
                            .error(R.drawable.person).into(binding.imgProfile);
                }

                readMesagges(nurse_id, chat_user_id, user.getProfile_path());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        seenMessage(chat_user_id, nurse_id);
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