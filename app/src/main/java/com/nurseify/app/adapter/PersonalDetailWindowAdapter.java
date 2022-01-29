package com.nurseify.app.adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.nurseify.app.R;
import com.nurseify.app.screen.facility.HomeFActivity;
import com.nurseify.app.screen.facility.RegistrationFActivity;
import com.nurseify.app.screen.facility.browse.Nurse_Browse_Fragment;
import com.nurseify.app.screen.nurse.RegisterActivity;
import com.nurseify.app.screen.nurse.SignupDetailsActivity;
import com.nurseify.app.screen.nurse.model.CityDatum;
import com.nurseify.app.screen.nurse.model.CountryModel.CountryDatum;
import com.nurseify.app.screen.nurse.model.SpecialtyDatum;
import com.nurseify.app.screen.nurse.model.State_Datum;
import com.nurseify.app.screen.nurse.model.WorkLocationDatum;

import java.util.List;


public class PersonalDetailWindowAdapter extends RecyclerView.Adapter<PersonalDetailWindowAdapter.MyViewHolder> {

    private int type;
    private List<WorkLocationDatum> workLocationData;
    private List<SpecialtyDatum> specialtyData;
    List<CityDatum> cityData;
    Activity context;
    Nurse_Browse_Fragment fragment;
    List<State_Datum> list_state;
    List<CountryDatum> list_country;
    String selected_state;
    String selected_city;
    String selected_city_int;

    public PersonalDetailWindowAdapter(Activity context, int type, List<WorkLocationDatum>
            workLocationData, List<SpecialtyDatum> specialtyData,
                                       UserPopupWindowAdapterInterface parentInterface) {
        this.context = context;
        this.type = type;
        this.workLocationData = workLocationData;
        this.specialtyData = specialtyData;
        this.parentInterface = parentInterface;
    }

    UserPopupWindowAdapterInterface parentInterface;

    public PersonalDetailWindowAdapter(RegisterActivity context, int type, int i,
                                       List<State_Datum> list_state, String selected_state,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = context;
        this.type = type;
        this.list_state = list_state;
        this.selected_state = selected_state;
        this.parentInterface = userPopupWindowAdapterInterface;
    }

    public PersonalDetailWindowAdapter(RegisterActivity context, int type,
                                       List<CountryDatum> list_state, String selected_state,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = context;
        this.type = type;
        this.list_country = list_state;
        this.selected_state = selected_state;
        this.parentInterface = userPopupWindowAdapterInterface;
    }


    public PersonalDetailWindowAdapter(RegisterActivity registerActivity,
                                       int type, int type1, int type2
            , List<CityDatum> cityData, String selected_city,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = registerActivity;
        this.type = type;
        this.cityData = cityData;
        this.selected_city = selected_city;
        this.parentInterface = userPopupWindowAdapterInterface;

    }

    public PersonalDetailWindowAdapter(RegistrationFActivity registerActivity,
                                       List<State_Datum> cityData, int selected_city,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = registerActivity;
        this.type = type;
        this.list_state = cityData;
        this.selected_state = "" + selected_city;
        this.parentInterface = userPopupWindowAdapterInterface;

    }

    public PersonalDetailWindowAdapter(RegistrationFActivity registerActivity, int a,
                                       List<CityDatum> cityData, int selected_city,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = registerActivity;
        this.type = a;
        this.cityData = cityData;
        this.selected_state = "" + selected_city;
        this.parentInterface = userPopupWindowAdapterInterface;

    }

    public PersonalDetailWindowAdapter(HomeFActivity registerActivity, Nurse_Browse_Fragment fragment, int a,
                                       List<CityDatum> cityData, int selected_city,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = registerActivity;
        this.fragment = fragment;
        this.type = a;
        this.cityData = cityData;
        this.selected_state = "" + selected_city;
        this.parentInterface = userPopupWindowAdapterInterface;

    }

    public PersonalDetailWindowAdapter(RegistrationFActivity registerActivity,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = registerActivity;
        this.selected_state = "" + selected_city;
        this.parentInterface = userPopupWindowAdapterInterface;
    }

    public PersonalDetailWindowAdapter(HomeFActivity activity, Nurse_Browse_Fragment nurse_browse_fragment,
                                       List<State_Datum> value, int selected_state,
                                       UserPopupWindowAdapterInterface userPopupWindowAdapterInterface) {
        this.context = activity;
        this.fragment = nurse_browse_fragment;
        this.type = type;
        this.list_state = value;
        this.selected_state = "" + selected_state;
        this.parentInterface = userPopupWindowAdapterInterface;
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setCityList(List<CityDatum> list) {
        this.cityData = list;
        notifyDataSetChanged();
    }


    public interface UserPopupWindowAdapterInterface {
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
        if ((context instanceof SignupDetailsActivity)) {
            SignupDetailsActivity activity = ((SignupDetailsActivity) context);
            if (type == 1) {
                WorkLocationDatum movie = workLocationData.get(position);
                holder.title.setText(movie.getName());
                if (!TextUtils.isEmpty(activity.select_prefer_geo)) {
                    int select = Integer.parseInt(activity.select_prefer_geo);
                    if (select == position) {
                        holder.lay_item.setBackgroundColor(holder.itemView.getContext()
                                .getResources().getColor(R.color.grad1));
                        holder.title.setTextColor(Color.WHITE);
                    } else {
                        holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
                        holder.lay_item.setBackground(null);
                    }
                } else {
                    holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
                    holder.lay_item.setBackground(null);
                }
            } else {
                SpecialtyDatum movie = specialtyData.get(position);
                holder.title.setText(movie.getName());
                if (activity.select_specialty != null && activity.select_specialty.size() != 0) {
                    if (activity.select_specialty.contains(position)) {
                        holder.lay_item.setBackgroundColor(holder.itemView.getContext()
                                .getResources().getColor(R.color.grad1));
                        holder.title.setTextColor(Color.WHITE);
                    } else {
                        holder.lay_item.setBackground(null);
                        holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
                    }
                } else {
                    holder.lay_item.setBackground(null);
                    holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentInterface.onCLickItem(position, type);
                }
            });
        } else if ((context instanceof RegisterActivity)) {
            RegisterActivity activity = (RegisterActivity) context;
            boolean isNotSelected = true;
            if (type == 3) {
                State_Datum movie = list_state.get(position);
                holder.title.setText(movie.getNames());
                if (!TextUtils.isEmpty(activity.selected_state)) {
                    int select = Integer.parseInt(activity.selected_state);
                    if (select == position) {
                        isNotSelected = false;
                    } else {
                        isNotSelected = true;
                    }
                } else {
                    isNotSelected = true;
                }
            } else if (type == 4) {
                CountryDatum movie = list_country.get(position);
                holder.title.setText(movie.getName());
                if (!TextUtils.isEmpty(activity.selected_Country)) {
                    int select = Integer.parseInt(activity.selected_Country);
                    if (select == position) {
                        isNotSelected = false;
                    } else {
                        isNotSelected = true;
                    }
                } else {
                    isNotSelected = true;
                }
            } else if (type == 7) {
                CityDatum movie = cityData.get(position);
                holder.title.setText(movie.getName());
                if (!TextUtils.isEmpty(activity.selected_City)) {
                    int select = Integer.parseInt(activity.selected_City);
                    if (select == position) {
                        isNotSelected = false;
                    } else {
                        isNotSelected = true;
                    }
                } else {
                    isNotSelected = true;
                }
            } else {
                SpecialtyDatum movie = specialtyData.get(position);
                holder.title.setText(movie.getName());
                if (activity.select_specialty != null && activity.select_specialty.size() != 0) {
                    if (activity.select_specialty.contains(position)) {
                        isNotSelected = false;
                    } else {
                        isNotSelected = true;
                    }
                } else {
                    isNotSelected = true;
                }
            }

            if (!isNotSelected) {
                holder.lay_item.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.grad1));
                holder.title.setTextColor(Color.WHITE);
            } else {
                holder.lay_item.setBackground(null);
                holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentInterface.onCLickItem(position, type);
                }
            });

        } else if ((context instanceof RegistrationFActivity)) {
            RegistrationFActivity activity = (RegistrationFActivity) context;
            boolean isNotSelected = true;
            if (type == 3) {
                State_Datum movie = list_state.get(position);
                holder.title.setText(movie.getNames());
                int select = activity.viewModel.selected_state;
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }

            } else if (type == 7) {
                CityDatum movie = cityData.get(position);
                holder.title.setText(movie.getName());
                int select = activity.viewModel.selected_City;
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            }

            if (!isNotSelected) {
                holder.lay_item.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.grad1));
                holder.title.setTextColor(Color.WHITE);
            } else {
                holder.lay_item.setBackground(null);
                holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentInterface.onCLickItem(position, type);
                }
            });

        } else if ((context instanceof HomeFActivity)) {
            Nurse_Browse_Fragment activity = (Nurse_Browse_Fragment) fragment;
            boolean isNotSelected = true;
            if (type == 3) {
                State_Datum movie = list_state.get(position);
                holder.title.setText(movie.getNames());
                int select = activity.viewModel.selected_state;
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }

            } else if (type == 7) {
                CityDatum movie = cityData.get(position);
                holder.title.setText(movie.getName());
                int select = activity.viewModel.selected_City;
                if (select == position) {
                    isNotSelected = false;
                } else {
                    isNotSelected = true;
                }
            }

            if (!isNotSelected) {
                holder.lay_item.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.grad1));
                holder.title.setTextColor(Color.WHITE);
            } else {
                holder.lay_item.setBackground(null);
                holder.title.setTextColor(activity.getResources().getColor(R.color.gray));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentInterface.onCLickItem(position, type);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            if (workLocationData == null || workLocationData.size() == 0)
                return 0;
            return workLocationData.size();
        } else if (type == 3) {
            if (list_state == null || list_state.size() == 0)
                return 0;
            return list_state.size();
        } else if (type == 4) {
            if (list_country == null || list_country.size() == 0)
                return 0;
            return list_country.size();
        } else if (type == 7) {
            if (cityData == null || cityData.size() == 0)
                return 0;
            return cityData.size();
        } else {
            if (specialtyData == null || specialtyData.size() == 0)
                return 0;
            return specialtyData.size();
        }
    }
}