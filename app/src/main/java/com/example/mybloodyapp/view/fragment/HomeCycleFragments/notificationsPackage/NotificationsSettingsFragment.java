package com.example.mybloodyapp.view.fragment.HomeCycleFragments.notificationsPackage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.NotificationsSettingsCheckBoxAdapter;
import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.notificationsSettings.NotificationsSettings;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsSettingsFragment extends BaseFragment {

    private String API_TOKEN;

    @BindView(R.id.relative_notification_blood_types)
    RelativeLayout relativeNotificationBloodTypes;
    @BindView(R.id.relative_notification_governortes)
    RelativeLayout relativeNotificationGovernortes;

    @BindView(R.id.img_bu_notification_settings_back)
    ImageButton imgBuNotificationSettingsBack;
    @BindView(R.id.image_notification_settings_show_blood_item)
    ImageView imageNotificationSettingsShowBloodItem;
    @BindView(R.id.recycler_notifications_settings_blood_types)
    RecyclerView recyclerNotificationsSettingsBloodTypes;
    @BindView(R.id.image_notification_settings_show_governorates_item)
    ImageView imageNotificationSettingsShowGovernoratesItem;
    @BindView(R.id.recycler_notifications_settings_governorates)
    RecyclerView recyclerNotificationsSettingsGovernorates;
    @BindView(R.id.bu_notifications_settings_save)
    Button buNotificationsSettingsSave;
    private List<String> bloodTypes = new ArrayList<>();
    private List<String> governorates = new ArrayList<>();
    private NotificationsSettingsCheckBoxAdapter governoratesAdapter;
    private NotificationsSettingsCheckBoxAdapter bloodTypesAdapter;

    public NotificationsSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications_settings, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        API_TOKEN = LoadData(getActivity(), "API_TOKEN");
        init();
        getNotificationSetting();

        imgBuNotificationSettingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        imageNotificationSettingsShowBloodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible(relativeNotificationBloodTypes, imageNotificationSettingsShowBloodItem);
            }
        });

        imageNotificationSettingsShowGovernoratesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visible(relativeNotificationGovernortes, imageNotificationSettingsShowGovernoratesItem);
            }
        });

        buNotificationsSettingsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall(false);
            }
        });

        return view;
    }

    private void init() {
        recyclerNotificationsSettingsBloodTypes.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerNotificationsSettingsGovernorates.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

    private void getNotificationSetting() {
        getClient().getNotificationsSettings(API_TOKEN).enqueue(new Callback<NotificationsSettings>() {
            @Override
            public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        bloodTypes = response.body().getData().getBloodTypes();
                        governorates = response.body().getData().getGovernorates();
                        getBloodTypes();
                        getGovernorates();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NotificationsSettings> call, Throwable t) {

            }
        });
    }

    private void getBloodTypes() {
        getClient().getBloodType().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    bloodTypesAdapter = new NotificationsSettingsCheckBoxAdapter(getActivity(), getActivity()
                            , response.body().getData(), bloodTypes);
                    recyclerNotificationsSettingsBloodTypes.setAdapter(bloodTypesAdapter);

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }

    private void getGovernorates() {
        getClient().getGovernorates().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    governoratesAdapter = new NotificationsSettingsCheckBoxAdapter(getActivity(), getActivity()
                            , response.body().getData(), governorates);
                    recyclerNotificationsSettingsGovernorates.setAdapter(governoratesAdapter);

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }

    /*@OnClick({R.id.img_bu_notification_settings_back, R.id.image_notification_settings_show_blood_item, R.id.image_notification_settings_show_governorates_item, R.id.bu_notifications_settings_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bu_notification_settings_back:
                onBack();
                break;
            case R.id.image_notification_settings_show_blood_item:
                visible(relativeNotificationBloodTypes, imageNotificationSettingsShowBloodItem);
                break;
            case R.id.image_notification_settings_show_governorates_item:
                visible(relativeNotificationGovernortes, imageNotificationSettingsShowGovernoratesItem);
                break;
            case R.id.bu_notifications_settings_save:
                onCall(false);
                break;
        }
    }*/

    @Override
    public void onBack() {
        super.onBack();
    }

    private void visible(View view, ImageView imageView) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_remove_white);
        } else {
            imageView.setImageResource(R.drawable.ic_add_white);
            view.setVisibility(View.GONE);
        }
    }

    private void onCall(final boolean state) {
        getClient().setNotificationsSettings(API_TOKEN,governoratesAdapter.newIds,bloodTypesAdapter.newIds).
                enqueue(new Callback<NotificationsSettings>() {
            @Override
            public void onResponse(Call<NotificationsSettings> call, Response<NotificationsSettings> response) {
                try {
                    if (response.body().getStatus() == 1) {

                    }
                    Toast.makeText(baseActivity,response.body().getMsg(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<NotificationsSettings> call, Throwable t) {

            }
        });
    }

}
