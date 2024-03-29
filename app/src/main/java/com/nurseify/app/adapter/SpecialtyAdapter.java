package com.nurseify.app.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nurseify.app.R;
import com.nurseify.app.common.CommonDatum;
import com.nurseify.app.screen.facility.browse.Nurse_Browse_Fragment;
import com.nurseify.app.screen.nurse.model.HourlyRate_DayOfWeek_OptionDatum;
import com.nurseify.app.screen.nurse.model.LanguageDatum;
import com.nurseify.app.screen.nurse.model.SpecialtyDatum;
import com.nurseify.app.screen.nurse.model.WorkLocationDatum;

import java.util.List;

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder> {

    private Nurse_Browse_Fragment fragment;
    private List<CommonDatum> list_Specialty_CD;
    private int type = 1;
    Context activity;
    List<Integer> select_specialty;
    List<Integer> select_geography;
    List<Integer> select_daysofWeeks;
    List<SpecialtyDatum> list_Specialty;
    List<HourlyRate_DayOfWeek_OptionDatum> list_daysOfWeek;
    private long mLastClickTime = 0;
    List<WorkLocationDatum> list_geography;
    List<LanguageDatum> listLang;
    List<Integer> selected_Lang;

    public SpecialtyAdapter(Context context, List<Integer> select_geography,
                            List<WorkLocationDatum> list_geography,
                            int type, SpecialtyListener specialtyListener) {
        this.type = type;
        this.activity = context;
        this.select_geography = select_geography;
        this.list_geography = list_geography;
        this.specialtyListener = specialtyListener;
    }

    public SpecialtyAdapter(Context context, List<Integer> select_geography,
                            List<HourlyRate_DayOfWeek_OptionDatum> list_geography,
                            int type, int s, SpecialtyListener specialtyListener) {
        this.type = type;
        this.activity = context;
        this.select_daysofWeeks = select_geography;
        this.list_daysOfWeek = list_geography;
        this.specialtyListener = specialtyListener;
    }

    public SpecialtyAdapter(Context context,
                            List<LanguageDatum> listLang, List<Integer> selected_Lang,
                            int type, int type1, int type21, SpecialtyListener specialtyListener) {
        this.type = type;
        this.activity = context;
        this.listLang = listLang;
        this.selected_Lang = selected_Lang;
        this.specialtyListener = specialtyListener;
    }


    public interface SpecialtyListener {
        void onClickItem(int position);
    }

    SpecialtyListener specialtyListener;

    public SpecialtyAdapter(Context activity, List<Integer> select_specialty,
                            List<SpecialtyDatum> list_Specialty, SpecialtyListener specialtyListener) {
        this.activity = activity;
        this.select_specialty = select_specialty;
        this.list_Specialty = list_Specialty;
        this.type = 1;
        this.specialtyListener = specialtyListener;
    }

    public SpecialtyAdapter(Activity activity, Nurse_Browse_Fragment fragment, List<Integer> select_specialty,
                            List<CommonDatum> list_Specialty, SpecialtyListener specialtyListener) {
        this.activity = activity;
        this.fragment = fragment;
        this.list_Specialty_CD = list_Specialty;
        this.select_specialty = select_specialty;
        this.type = 11;
        this.specialtyListener = specialtyListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specialty, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        try {
            if (type == 1) {
                SpecialtyDatum model = list_Specialty.get(select_specialty.get(position));
                holder.tv_text.setText(model.getName());
            } else if (type == 2) {
                WorkLocationDatum model = list_geography.get(select_geography.get(position));
                holder.tv_text.setText(model.getName());
            } else if (type == 7) {
                LanguageDatum model = listLang.get(selected_Lang.get(position));
                holder.tv_text.setText(model.getLanguage());
            } else if (type == 11 && list_Specialty_CD != null && list_Specialty_CD.size() != 0) {
                CommonDatum model = list_Specialty_CD.get(select_specialty.get(position));
                holder.tv_text.setText(model.getName());
            } else {
                HourlyRate_DayOfWeek_OptionDatum model = list_daysOfWeek.get(select_daysofWeeks.get(position));
                holder.tv_text.setText(model.getName());
            }

            holder.lay_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    specialtyListener.onClickItem(position);
                }
            });

        } catch (Exception e) {
            Log.e("Service_Adapter", e.toString());
        }
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            if (select_specialty == null || select_specialty.size() == 0) {
                return 0;
            } else
                return select_specialty.size();
        } else if (type == 2) {
            if (select_specialty == null || select_specialty.size() == 0)
                return 0;
            else
                return select_specialty.size();
        } else if (type == 3) {
            if (select_daysofWeeks == null || select_daysofWeeks.size() == 0)
                return 0;
            else
                return select_daysofWeeks.size();
        } else if (type == 7) {
            if (selected_Lang == null || selected_Lang.size() == 0)
                return 0;
            else
                return selected_Lang.size();
        } else if (type == 11) {
            if (select_specialty != null && select_specialty.size() != 0) {
                return select_specialty.size();
            } else
                return 0;
        } else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lay_remove;
        TextView tv_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lay_remove = itemView.findViewById(R.id.lay_remove);
            tv_text = itemView.findViewById(R.id.tv_text);

        }
    }
}
