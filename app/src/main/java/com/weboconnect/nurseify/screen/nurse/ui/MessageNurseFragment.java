package com.weboconnect.nurseify.screen.nurse.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.adapter.MessageAdapter;
import com.weboconnect.nurseify.databinding.FragmentMessageBinding;
import com.weboconnect.nurseify.intermediate.OnItemClick;
import com.weboconnect.nurseify.screen.nurse.model.Chatlist;
import com.weboconnect.nurseify.screen.nurse.model.User;
import com.weboconnect.nurseify.utils.SessionManager;

import java.util.ArrayList;

public class MessageNurseFragment extends Fragment {
    String id;
    FragmentMessageBinding binding;
    View view;
    private ArrayList<User> mUsers;
    private ArrayList<User> mUsers_search;
    private DatabaseReference reference;
    private ArrayList<String> usersList;
    MessageAdapter messageAdapter;
    private OnItemClick onItemClick;

    public MessageNurseFragment() {

    }

    public MessageNurseFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, null, false);
        binding.recyclerViewJobs.setAdapter(new MessageAdapter(getActivity(), true));

        String user_id = new SessionManager(getContext()).get_user_register_Id().toString();
        usersList = new ArrayList<String>();
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        Chatlist chatlist = snapshot.getValue(Chatlist.class);
                        if (chatlist.getSender().equals(user_id))
                            usersList.add(chatlist.getReceiver());
                        if (chatlist.getReceiver().equals(user_id))
                            usersList.add(chatlist.getSender());
                    } catch (Exception e) {
                        Log.d("TAG", "onDataChange: " + e.getMessage());
                    }
                }
                if (usersList.size() == 0) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view = binding.getRoot();
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        ArrayList<String> list_id = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        User user = snapshot.getValue(User.class);
                        for (String chatlist : usersList) {
                            if (user.getId().equals(chatlist)) {
                                if (mUsers.size() != 0) {
                                    boolean stst = false;
                                    for (User user1 : mUsers) {
                                        if (user.getId().equals(user1.getId())) {
                                            stst = true;
                                        }
                                    }
                                    if (!stst)
                                        mUsers.add(user);
                                } else {
                                    mUsers.add(user);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d("TAG", "onDataChange: " + e.getMessage());
                    }
                }
                if (usersList.size() == 0) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvMsg.setVisibility(View.VISIBLE);
                    binding.tvMsg.setText("No Messages");
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvMsg.setVisibility(View.GONE);
                }
                messageAdapter = new MessageAdapter(getContext(), onItemClick, mUsers, true);
                binding.recyclerViewJobs.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchUsers(String s) {
        if (TextUtils.isEmpty(s)) {
            messageAdapter = new MessageAdapter(getContext(), onItemClick, mUsers, true);
            binding.recyclerViewJobs.setAdapter(messageAdapter);
            binding.progressBar.setVisibility(View.GONE);
            return;
        }
        final String fuser = new SessionManager(getContext()).get_User().getId();
        Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("full_name")
                .startAt(s).endAt(s + "\uf8ff");
        binding.progressBar.setVisibility(View.VISIBLE);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mUsers_search != null && mUsers_search.size() != 0)
                    mUsers_search.clear();
                else mUsers_search = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert fuser != null;
                    if (!user.getId().equals(fuser) && usersList.contains(user.getId())) {
                        mUsers_search.add(user);
                    }
                }
                messageAdapter = new MessageAdapter(getContext(), onItemClick, mUsers_search, true);
                binding.recyclerViewJobs.setAdapter(messageAdapter);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

}