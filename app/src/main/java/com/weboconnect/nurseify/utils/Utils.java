package com.weboconnect.nurseify.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.google.gson.reflect.TypeToken;
import com.weboconnect.nurseify.screen.nurse.model.FacilityModel;
import com.weboconnect.nurseify.screen.nurse.model.JobModel;
import com.weboconnect.nurseify.screen.nurse.model.QuestionModel;
import com.weboconnect.nurseify.screen.nurse.model.UserProfileData;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {
    private static Toast toast;
    public static Type typeUserProfileData = new TypeToken<UserProfileData>() {
    }.getType();
    public static Type typeJob = new TypeToken<JobModel.JobDatum>() {
    }.getType();
    public static Type typeFacilityJob = new TypeToken<FacilityModel.Facility>() {
    }.getType();

    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        double value = size / Math.pow(1024, digitGroups);
        if (digitGroups >= 2 && value > 5000) {
            return "0";
        }
        return new DecimalFormat("###0.#").format(size / Math.pow(1024, digitGroups)).replaceAll(",", "")
                + " " + units[digitGroups];

    }

    public static String getFileSize_size(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        double value = size / Math.pow(1024, digitGroups);
        if (digitGroups >= 2 && value > 5000) {
            return "0";
        }
        return new DecimalFormat("###0.#").format(size / Math.pow(1024, digitGroups)).replaceAll(",", "");

    }

    public static String getFileSize_Unit(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        double value = size / Math.pow(1024, digitGroups);
        if (digitGroups >= 2 && value > 5000) {
            return "0";
        }
        return "" + units[digitGroups];

    }

    public static void onClickEvent(final View v) {
        v.setEnabled(false);
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setEnabled(true);
            }
        }, 150);
    }

    public static void displayToast(Context context, String txt) {
        if (toast != null && toast.getView() != null && toast.getView().isShown()) {
            toast.cancel();
            toast = null;
        }
        if (!TextUtils.isEmpty(txt)) {
            toast = Toast.makeText(context, txt, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean checkProfileCreated(UserData data) {
       /* if (TextUtils.isEmpty(data.getName())) {
            return false;
        }
        if (TextUtils.isEmpty(data.getEmail())) {
            return false;
        }
        if (TextUtils.isEmpty(data.getProfessional())) {
            return false;
        }

        if (TextUtils.isEmpty(data.getPhone())) {
            return false;
        }

        if (TextUtils.isEmpty(data.getAddress())) {
            return false;
        }*/

        return true;
    }

    public static List<QuestionModel> getQuesList() {
        List<QuestionModel> questionModelList = new ArrayList<>();
        questionModelList.add(new QuestionModel("Are you interested in serving as a preceptor for a graduate nurse?"));
        questionModelList.add(new QuestionModel("Are you interested in serving as an interim nurse leader?"));
        questionModelList.add(new QuestionModel("Please select the leadership role you have experience in and are willing to work as an interim leader?"));
        questionModelList.add(new QuestionModel("Clinical educator"));
        questionModelList.add(new QuestionModel("Daisy Award winner?"));
        questionModelList.add(new QuestionModel("Employee of the mth, qtr, yr?"));
        questionModelList.add(new QuestionModel("Other nursing awards?"));
        questionModelList.add(new QuestionModel("Professional practice council?"));
        questionModelList.add(new QuestionModel("Research / publications?"));
        return questionModelList;
    }

    public static List<QuestionModel> getQuesListByProfile(UserProfileData.RoleInterest roleInterest) {
        List<QuestionModel> questionModelList = new ArrayList<>();
        if (TextUtils.isEmpty(roleInterest.getServingPreceptor()) || roleInterest.getServingPreceptor().equals("0")) {
            questionModelList.add(new QuestionModel("Are you interested in serving as a preceptor for a" +
                    " graduate nurse?", 0));
        } else {
            questionModelList.add(new QuestionModel("Are you interested in serving as a preceptor for a" +
                    " graduate nurse?", 1));
        }
        if (TextUtils.isEmpty(roleInterest.getServingInterimNurseLeader()) || roleInterest.getServingInterimNurseLeader().equals("0")) {
            questionModelList.add(new QuestionModel("Are you interested in serving as an interim nurse leader?",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Are you interested in serving as an interim nurse leader?",
                    1));
        }
        if (TextUtils.isEmpty(roleInterest.getLeadershipRoles()) || roleInterest.getLeadershipRoles().equals("0")) {
            questionModelList.add(new QuestionModel("Please select the leadership role you have experience in and are willing to work as an interim leader?"
                    , 0, 0));
        } else {
            try {
                questionModelList.add(new QuestionModel("Please select the leadership role you have experience in and are willing to work as an interim leader?"
                        , 0, Integer.parseInt(roleInterest.getLeadershipRoles())));
            } catch (Exception e) {
                questionModelList.add(new QuestionModel("Please select the leadership role you have experience in and are willing to work as an interim leader?"
                        , 0, 0));
            }
        }
        if (TextUtils.isEmpty(roleInterest.getClinicalEducator()) || roleInterest.getClinicalEducator().equals("0")) {
            questionModelList.add(new QuestionModel("Clinical educator",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Clinical educator",
                    1));
        }
        if (TextUtils.isEmpty(roleInterest.getIsDaisyAwardWinner()) || roleInterest.getIsDaisyAwardWinner().equals("0")) {
            questionModelList.add(new QuestionModel("Daisy Award winner?",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Daisy Award winner?",
                    1));
        }
        if (TextUtils.isEmpty(roleInterest.getEmployeeOfTheMthQtrYr()) || roleInterest.getEmployeeOfTheMthQtrYr().equals("0")) {
            questionModelList.add(new QuestionModel("Employee of the mth, qtr, yr?",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Employee of the mth, qtr, yr?",
                    1));
        }
        if (TextUtils.isEmpty(roleInterest.getOtherNursingAwards()) || roleInterest.getOtherNursingAwards().equals("0")) {
            questionModelList.add(new QuestionModel("Other nursing awards?",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Other nursing awards?",
                    1));
        }
        if (TextUtils.isEmpty(roleInterest.getIsProfessionalPracticeCouncil()) || roleInterest.getIsProfessionalPracticeCouncil().equals("0")) {
            questionModelList.add(new QuestionModel("Professional practice council?",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Professional practice council?",
                    1));
        }
        if (TextUtils.isEmpty(roleInterest.getIsResearchPublications()) || roleInterest.getIsResearchPublications().equals("0")) {
            questionModelList.add(new QuestionModel("Research / publications?",
                    0));
        } else {
            questionModelList.add(new QuestionModel("Research / publications?",
                    1));
        }

        return questionModelList;
    }
}
