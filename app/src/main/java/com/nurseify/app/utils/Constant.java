package com.nurseify.app.utils;

import static android.os.Build.VERSION.SDK_INT;

import android.os.Build;

import com.nurseify.app.webService.RetrofitClient;

public class Constant {

    public static final int REQUEST_CODE_ADD_JOB = 656;
    public static final int REQUEST_EDIT = 333;
    public static final int REQUEST_Browse_Job_FUNS = 456;
    public static final String POSITION = "POSITION";
    public static final String SCROLL_TO = "SCROLL_TO";
    public static final String ADD = "Add";
    public static final String FLAG = "flag";
    public static final String DEVICE_INFO = "Device: " + Build.DEVICE + " Model: " + Build.MODEL + " Version: " + SDK_INT + "";
    public static final String USER_NODE = "Users_db";
    public static final String ONLINE_STATUS = "status";
    public static final String JOB_ID = "job_id";
    public static final String ID = "id";
    public static final String URL_TERMS_CONDITION = RetrofitClient.BASE_URL1 + "terms-conditions";
    public static final String CHAT_NODE = "Chat_db";
    public static final String TIME_STAMP = "timestamp";
    public static final String IS_SEEN = "is_seen";
    public static final String CHAT_USERS_CHILD = "chat_users";
    public static final String FULL_NAME = "full_name";
    public static final String PROFILE_PATH = "profile_path";
    // person session constant
    public static String CONST_NURSE_TYPE = "Nurse";
    public static String CONST_FACULTY_TYPE = "Faculty";
    public static String TYPE = "type";
    public static String User_Register_ID = "register_id";
    public static final String API_KEY = "api_key";
    public static final String SECTION = "section";
    public static final String PERSON_DETAIL = "personal_detail";
    public static final String HOURLY_RATE_AVAILABILITY = "hourly_rate_availability";
    public static final String Work_History_Experience = "Work_History_Experience";
    public static final String Work_History_Certifications = "Work_History_Certifications";
    public static final String Work_History_Resume = "Work_History_Resume";
    public static final String Role_Interest1 = "Role_Interest1";
    public static final String Role_Interest2 = "Role_Interest2";
    // extra constant
    public static String USER_NAME = "user_name";
    public static String EMAIL_ID = "email";
    public static String PASSWORD = "password";
    public static String FIRST_NAME = "first_name";
    public static String LAST_NAME = "last_name";
    public static String MOBILE = "mobile";
    public static String NURSING_LICENSE_STATE = "nursing_license_state";
    public static String NURSING_LICENSE_NUMBER = "nursing_license_number";
    public static String SPECIALITY = "specialty";
    public static String WORK_LOACTION = "work_location";


    // intent param
    public static String STR_RESPONSE_DATA = "response_data";
    public static String STR_WORK_CERTIFICATE = "STR_WORK_CERTIFICATE";

    // data constant
    public static final String EDIT_MODE = "Edit_Mode";


    public static int REQUEST_Facility_FUNS = 457;

    public static String FACILITY_ID = "facility_id";
    public static String FACILITY_DATA = "facility_data";
    public static final String WHEM_WRONG = "When_WRONG";
    public static String UnSuccessfull = "unsuccessfull";
    public static final String API_STATUS = "apip_status";
    public static final String UNITED_STATE_CODE = "233";
    public static final String Notification = "Notification";
}
