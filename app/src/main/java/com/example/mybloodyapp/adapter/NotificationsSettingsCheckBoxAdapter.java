package com.example.mybloodyapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.city.CityData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationsSettingsCheckBoxAdapter extends RecyclerView.Adapter<NotificationsSettingsCheckBoxAdapter.CheckBoxRowHolder> {
    private Context context;
    private Activity activity;
    private List<CityData> generalResponseDataList = new ArrayList<>();
    private List<String> oldIds = new ArrayList<>();
    public List<Integer> newIds = new ArrayList<>();

    public NotificationsSettingsCheckBoxAdapter(Context context,Activity activity, List<CityData> generalResponseDataList, List<String> oldIds) {
        this.context = context;
        this.generalResponseDataList = generalResponseDataList;
        this.oldIds = oldIds;
        this.activity =activity;
    }

    @NonNull
    @Override
    public CheckBoxRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_settings_item, null);
        NotificationsSettingsCheckBoxAdapter.CheckBoxRowHolder checkBoxRowHolder = new NotificationsSettingsCheckBoxAdapter.CheckBoxRowHolder(view);
        return checkBoxRowHolder;
    }

    public void setData(CheckBoxRowHolder holder, int position) {
        holder.checkboxNotificationSettings.setText(generalResponseDataList.get(position).getName());

        if (oldIds.contains(String.valueOf(generalResponseDataList.get(position).getId()))) {
            holder.checkboxNotificationSettings.setChecked(true);
            newIds.add(generalResponseDataList.get(position).getId());
        }
        else{
            holder.checkboxNotificationSettings.setChecked(false);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final CheckBoxRowHolder holder, int position) {
            setData(holder,position);
            holder.checkboxNotificationSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!holder.checkboxNotificationSettings.isChecked()) {
                        for (int i = 0; i <newIds.size() ; i++) {
                            if (newIds.get(i)==generalResponseDataList.get(holder.getAdapterPosition()).getId()) {
                                newIds.remove(i);
                                break;
                            }
                        }
                    }
                    else{
                        newIds.add(generalResponseDataList.get(holder.getAdapterPosition()).getId());
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return generalResponseDataList.size();
    }


    public class CheckBoxRowHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.checkbox_notification_settings)
        CheckBox checkboxNotificationSettings;

        public CheckBoxRowHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}
