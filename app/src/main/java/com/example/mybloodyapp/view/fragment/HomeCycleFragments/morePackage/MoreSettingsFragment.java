package com.example.mybloodyapp.view.fragment.HomeCycleFragments.morePackage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.notificationsSettings.NotificationsSettings;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.activity.LoginCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.articlePost.FavoritePostFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.notificationsPackage.NotificationsSettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.clean;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreSettingsFragment extends BaseFragment {


    @BindView(R.id.tv_more_settings_favorite)
    TextView tvMoreSettingsFavorite;
    @BindView(R.id.tv_more_settings_contact_us)
    TextView tvMoreSettingsContactUs;
    @BindView(R.id.tv_more_settings_about_app)
    TextView tvMoreSettingsAboutApp;
    @BindView(R.id.tv_more_settings_rate_app)
    TextView tvMoreSettingsRateApp;
    @BindView(R.id.tv_more_settings_notification_settings)
    TextView tvMoreSettingsNotificationSettings;
    @BindView(R.id.tv_more_settings_log_out)
    TextView tvMoreSettingsLogOut;

    public MoreSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_settings, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        tvMoreSettingsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritePostFragment favoritePostFragment =new FavoritePostFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,favoritePostFragment);
            }
        });

        tvMoreSettingsContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactUsFragment contactUsFragment =new ContactUsFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,contactUsFragment);
            }
        });

        tvMoreSettingsAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutAppFragment aboutAppFragment =new AboutAppFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,aboutAppFragment);
            }
        });

        tvMoreSettingsRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvMoreSettingsNotificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationsSettingsFragment notificationsSettings =new NotificationsSettingsFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,notificationsSettings);
            }
        });

        tvMoreSettingsLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =getActivity().getSharedPreferences("Checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("remember");
                editor.apply();
                clean(getActivity());
                Intent intent =new Intent(getActivity(), LoginCycleActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return view;
    }

    /*@OnClick({R.id.tv_more_settings_favorite, R.id.tv_more_settings_contact_us, R.id.tv_more_settings_about_app, R.id.tv_more_settings_rate_app, R.id.tv_more_settings_notification_settings, R.id.tv_more_settings_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more_settings_favorite:
                FavoritePostFragment favoritePostFragment =new FavoritePostFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,favoritePostFragment);
                break;
            case R.id.tv_more_settings_contact_us:
                ContactUsFragment contactUsFragment =new ContactUsFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,contactUsFragment);
                break;
            case R.id.tv_more_settings_about_app:
                AboutAppFragment aboutAppFragment =new AboutAppFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,aboutAppFragment);
                break;
            case R.id.tv_more_settings_rate_app:


                break;
            case R.id.tv_more_settings_notification_settings:
                NotificationsSettingsFragment notificationsSettings =new NotificationsSettingsFragment();
                replaceFragment(getFragmentManager(),
                        R.id.frame_of_home_fragment_container,notificationsSettings);
                break;
            case R.id.tv_more_settings_log_out:
                clean(getActivity());
                Intent intent =new Intent(getActivity(), LoginCycleActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }*/

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
