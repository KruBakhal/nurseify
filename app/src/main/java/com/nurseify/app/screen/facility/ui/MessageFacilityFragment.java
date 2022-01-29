package com.nurseify.app.screen.facility.ui;

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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nurseify.app.R;
import com.nurseify.app.adapter.MessageAdapter;
import com.nurseify.app.databinding.FragmentMessageBinding;
import com.nurseify.app.intermediate.OnItemClick;
import com.nurseify.app.screen.nurse.model.Chatlist;
import com.nurseify.app.screen.nurse.model.User;
import com.nurseify.app.utils.Constant;
import com.nurseify.app.utils.SessionManager;

import java.util.ArrayList;
import java.util.Map;

public class MessageFacilityFragment extends Fragment {
    String id;
    FragmentMessageBinding binding;
    View view;
    private ArrayList<User> mUsers_search;
    private ArrayList<User> usersList;
    MessageAdapter messageAdapter;
    private OnItemClick onItemClick;

    public MessageFacilityFragment() {

    }

    public MessageFacilityFragment(String id) {
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, null, false);
        binding.recyclerViewJobs.setAdapter(new MessageAdapter(getActivity(), false));
        usersList = new ArrayList<User>();
        String userId = new SessionManager(getContext()).get_user_register_Id();
        ArrayList<Chatlist> chat_user = new ArrayList<Chatlist>();
        FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                .child(userId).child(Constant.CHAT_USERS_CHILD)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chat_user.clear();
                        if (dataSnapshot != null && dataSnapshot.hasChildren()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                try {
                                    Chatlist mgs_model = snapshot.getValue(Chatlist.class);
                                    chat_user.add(mgs_model);
                                } catch (Exception e) {
                                    Log.d("TAG", "onDataChange: " + e.getMessage());
                                    dismissProgress();
                                }
                            }
                        } else {
                            no_message();
                        }
                        if (chat_user != null && chat_user.size() != 0) {
                            FirebaseDatabase.getInstance().getReference(Constant.USER_NODE)
                                    .addValueEventListener(new ValueEventListener() {
                                        int count = 0;

                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            try {
                                                usersList.clear();
                                                if (snapshot != null && snapshot.hasChildren()) {
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                        User mgs_model = new User();
                                                        Map<String, Object> objectMap = (Map<String, Object>) dataSnapshot.getValue();
                                                        if (objectMap != null && !objectMap.get(Constant.ID).equals(userId)) {
                                                            mgs_model.setId(objectMap.get(Constant.ID).toString());
                                                            mgs_model.setEmail(objectMap.get(Constant.EMAIL_ID).toString());
                                                            mgs_model.setFull_name(objectMap.get(Constant.FULL_NAME).toString());
                                                            mgs_model.setProfile_path(objectMap.get(Constant.PROFILE_PATH).toString());
                                                            mgs_model.setStatus((Boolean) objectMap.get(Constant.ONLINE_STATUS));
                                                            mgs_model.setType(objectMap.get(Constant.TYPE).toString());

                                                            for (Chatlist chat_model : chat_user) {
                                                                if (chat_model != null && chat_model.getSender().equals(mgs_model.getId())
                                                                        || chat_model.getReceiver().equals(mgs_model.getId())) {
//                                                                List<Chat_Model> sds = new ArrayList<>();
//                                                                sds.add(chat_model);
                                                                    mgs_model.setChat_users(chat_model);
                                                                    usersList.add(mgs_model);
                                                                    count++;
                                                                    if (count >= chat_user.size()) {
                                                                        break;
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }
                                                } else {
                                                    no_message();
                                                }
                                                dismissProgress();
                                                messageAdapter = new MessageAdapter(getContext(), onItemClick, usersList, false);
                                                binding.recyclerViewJobs.setAdapter(messageAdapter);
                                            } catch (Exception exception) {
                                                Log.d("TAG", "onDataChange: " + exception.getMessage());
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            error_progress();
                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        error_progress();
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

    private void no_message() {
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        binding.tvMsg.setText("No Message Found!");
    }

    private void dismissProgress() {
        binding.layProgress.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
        binding.tvMsg.setText("No Message Found!");
    }

    private void showProgress() {
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        binding.tvMsg.setText("Please Wait");
    }

    private void error_progress() {
        binding.layProgress.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);
        binding.tvMsg.setText("Something when wrong !");
    }



    private void searchUsers(String s) {
        if (TextUtils.isEmpty(s)) {
            messageAdapter = new MessageAdapter(getContext(), onItemClick, usersList, true);
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