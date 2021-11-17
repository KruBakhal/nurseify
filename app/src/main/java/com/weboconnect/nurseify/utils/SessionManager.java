package com.weboconnect.nurseify.utils;

import static android.content.Context.MODE_PRIVATE;

import static com.weboconnect.nurseify.utils.Constant.API_KEY;
import static com.weboconnect.nurseify.utils.Constant.STR_RESPONSE_DATA;
import static com.weboconnect.nurseify.utils.Constant.TYPE;
import static com.weboconnect.nurseify.utils.Constant.User_Register_ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;

import java.lang.reflect.Type;

public class SessionManager {
    Context context;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("" + R.string.app_name, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setSession_IN(String id, UserProfileData data) {
        save_user_register_id(id);
        save_user(data);
    }

    public void setSession_OUT() {
        save_user_register_id(null);
        save_user(null);
        set_TYPE(null);
    }


    public void save_user(UserProfileData data) {
        editor.putString(STR_RESPONSE_DATA, new Gson().toJson(data));
        editor.apply();
    }


    public UserProfileData get_User() {
        String data = sharedPreferences.getString(STR_RESPONSE_DATA, null);
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        Type type = new TypeToken<UserProfileData>() {
        }.getType();

        UserProfileData signupResponseModel = new Gson().fromJson(data, type);

        return signupResponseModel;

    }

    public String get_API_KEY() {
        return sharedPreferences.getString(API_KEY, "1a2b3c4d5e6f7g8h9i");
    }

    public void set_API_KEY(String key) {
        editor.putString(API_KEY, key).apply();
    }

    public String get_TYPE() {
        return sharedPreferences.getString(TYPE, null);
    }

    public void set_TYPE(String key) {
        editor.putString(TYPE, key).apply();
    }


    public boolean isUserLoginedIn() {
        if (TextUtils.isEmpty(get_user_register_Id())) {
            return false;
        }
        return true;
    }


    public void save_user_register_id(String id) {
        editor.putString(User_Register_ID, id);
        editor.apply();
    }

    public String get_user_register_Id() {
        return sharedPreferences.getString(User_Register_ID, null);
    }


    public boolean is_User_Profile_Created() {
        UserProfileData data = get_User();
        if (data == null) {
            return false;
        }

       /* if (checkProfileCreated(data)) {
            return true;
        }*/
        return false;
    }


}