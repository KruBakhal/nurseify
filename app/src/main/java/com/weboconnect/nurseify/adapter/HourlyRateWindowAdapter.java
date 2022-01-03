package com.weboconnect.nurseify.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weboconnect.nurseify.R;
import com.weboconnect.nurseify.screen.facility.Add_Jobs_Activity;
import com.weboconnect.nurseify.screen.facility.HomeFActivity;
import com.weboconnect.nurseify.screen.facility.browse.Nurse_Browse_Fragment;
import com.weboconnect.nurseify.screen.nurse.RegisterActivity;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_Common_OptionDatum;
import com.weboconnect.nurseify.screen.nurse.model.HourlyRate_DayOfWeek_OptionDatum;
import com.weboconnect.nurseify.screen.nurse.model.WorkLocationDatum;

import java.util.List;


public class HourlyRateWindowAdapter extends RecyclerView.Adapter<HourlyRateWindowAdapter.MyViewHolder> {

    private  Nurse_Browse_Fragment fragment;
    private List<WorkLocationDatum> list_geography;
    private List<HourlyRate_DayOfWeek_OptionDatum> list_days_of_week;
    private int type;
    private List<HourlyRate_Common_OptionDatum> datumList;
    Activity activity;
    HourlyRateWindowInterface parentInterface;


    public HourlyRateWindowAdapter(RegisterActivity context, int type,
                                   List<HourlyRate_Common_OptionDatum> list_days_of_week,
                                   HourlyRateWindowInterface userPopupWindowAdapterInterface) {
        this.type = type;
        this.datumList = list_days_of_week;
        this.activity = context;
        this.parentInterface = userPopupWindowAdapterInterface;
    }

    public HourlyRateWindowAdapter(RegisterActivity context, int type, int s, List<WorkLocationDatum> list_geography, HourlyRateWindowInterface userPopupWindowAdapterInterface) {
        this.type = type;
        this.list_geography = list_geography;
        this.activity = context;
        this.parentInterface = userPopupWindowAdapterInterface;
    }

    public HourlyRateWindowAdapter(RegisterActivity registerActivity, int type, int type1, int type2,
                                   List<HourlyRate_DayOfWeek_OptionDatum>
                                           list_days_of_week, HourlyRateWindowInterface hourlyRateWindowInterface) {
        this.type = type;
        this.list_days_of_week = list_days_of_week;
        this.activity = registerActivity;
        this.parentInterface = hourlyRateWindowInterface;
    }

    public HourlyRateWindowAdapter(Activity registerActivity, int type, int type1, int type2,
                                   List<HourlyRate_DayOfWeek_OptionDatum>
                                           list_days_of_week, HourlyRateWindowInterface hourlyRateWindowInterface) {
        this.type = type;
        this.list_days_of_week = list_days_of_week;
        this.activity = registerActivity;
        this.parentInterface = hourlyRateWindowInterface;
    }

    public HourlyRateWindowAdapter(Activity registerActivity, Nurse_Browse_Fragment fragment, int type,
                                   List<HourlyRate_DayOfWeek_OptionDatum>
                                           list_days_of_week, HourlyRateWindowInterface hourlyRateWindowInterface) {
        this.type = type;
        this.fragment = fragment;
        this.list_days_of_week = list_days_of_week;
        this.activity = registerActivity;
        this.parentInterface = hourlyRateWindowInterface;
    }

    public interface HourlyRateWindowInterface {
        void onCLickItem(int i, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lay_item;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvText);
            lay_item = view.findViewById(R.id.lay_item);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popup_dropdown, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        boolean isNotSelected = true;
        if (activity instanceof RegisterActivity) {
            isNotSelected = register_activity(holder, position, isNotSelected);
        } else if (activity instanceof Add_Jobs_Activity) {
            isNotSelected = add_jod_activity(holder, position, isNotSelected);
        } else if (activity instanceof HomeFActivity) {
            isNotSelected = homeF_activity(holder, position, isNotSelected);
        }

        if (!isNotSelected) {
            holder.lay_item.setBackgroundColor(holder.itemView.getContext()
                    .getResources().getColor(R.color.grad1));
            holder.title.setTextColor(Color.WHITE);
        } else {
            holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
            holder.lay_item.setBackground(null);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentInterface.onCLickItem(position, type);
            }
        });


    }

    private boolean homeF_activity(MyViewHolder holder, int position, boolean isNotSelected) {
        Nurse_Browse_Fragment activity = ((Nurse_Browse_Fragment) fragment);
        if (type == 4) {
            HourlyRate_DayOfWeek_OptionDatum movie = list_days_of_week.get(position);
            holder.title.setText(movie.getName());
            if (activity.viewModel.select_daysOfWeek != null && activity.viewModel.select_daysOfWeek.size() != 0) {
                if (activity.viewModel.select_daysOfWeek.contains(position)) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        }
        return isNotSelected;
    }

    private boolean add_jod_activity(MyViewHolder holder, int position, boolean isNotSelected) {
        Add_Jobs_Activity activity = ((Add_Jobs_Activity) HourlyRateWindowAdapter.this.activity);
        if (type == 4) {
            HourlyRate_DayOfWeek_OptionDatum movie = list_days_of_week.get(position);
            holder.title.setText(movie.getName());
            if (activity.viewModel.select_daysOfWeek != null && activity.viewModel.select_daysOfWeek.size() != 0) {
                if (activity.viewModel.select_daysOfWeek.contains(position)) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        }
        return isNotSelected;
    }

    private boolean register_activity(MyViewHolder holder, int position, boolean isNotSelected) {

        RegisterActivity activity = ((RegisterActivity) HourlyRateWindowAdapter.this.activity);
        if (type == 1) {
            HourlyRate_Common_OptionDatum movie = datumList.get(position);
            holder.title.setText(movie.getName());
            if (!TextUtils.isEmpty(activity.selected_shift_duration)) {
                int select = Integer.parseInt(activity.selected_shift_duration);
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        } else if (type == 2) {
            HourlyRate_Common_OptionDatum movie = datumList.get(position);
            holder.title.setText(movie.getName());
            if (!TextUtils.isEmpty(activity.selected_assignment_duration)) {
                int select = Integer.parseInt(activity.selected_assignment_duration);
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        } else if (type == 3) {
            HourlyRate_Common_OptionDatum movie = datumList.get(position);
            holder.title.setText(movie.getName());
            if (!TextUtils.isEmpty(activity.selected_preferred_shift)) {
                int select = Integer.parseInt(activity.selected_preferred_shift);
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        } else if (type == 5) {
            WorkLocationDatum movie = list_geography.get(position);
            holder.title.setText(movie.getName());
            if (!TextUtils.isEmpty(activity.selected_geography)) {
                int select = Integer.parseInt(activity.selected_geography);
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        } else if (type == 4) {
            HourlyRate_DayOfWeek_OptionDatum movie = list_days_of_week.get(position);
            holder.title.setText(movie.getName());
            if (activity.select_daysOfWeek != null && activity.select_daysOfWeek.size() != 0) {
                if (activity.select_daysOfWeek.contains(position)) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            } else {
                isNotSelected = true;
            }
        }
        return isNotSelected;
    }

    @Override
    public int getItemCount() {
        if (type == 4) {
            if (list_days_of_week == null || list_days_of_week.size() == 0)
                return 0;
            return list_days_of_week.size();
        } else if (type == 5) {
            if (list_geography == null || list_geography.size() == 0)
                return 0;
            return list_geography.size();
        } else {
            if (datumList == null || datumList.size() == 0)
                return 0;
            return datumList.size();
        }
    }
}