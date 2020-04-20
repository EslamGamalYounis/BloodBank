package com.example.mybloodyapp.view.fragment.HomeCycleFragments.morePackage;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.settings.Settings;
import com.example.mybloodyapp.data.model.settings.SettingsData;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutAppFragment extends BaseFragment {


    private  String API_TOKEN  ;
    @BindView(R.id.img_bu_about_app_back)
    ImageButton imgBuAboutAppBack;
    @BindView(R.id.tv_about_app_text)
    TextView tvAboutAppText;
    private SettingsData settingData;

    public AboutAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        API_TOKEN = LoadData(getActivity(),"API_TOKEN");
        // Inflate the layout for this fragment
        getSettingData();

        imgBuAboutAppBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        return view;
    }

    private void getSettingData(){
        getClient().getSettingsInfo(API_TOKEN).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                try {
                    settingData =response.body().getData();
                    tvAboutAppText.setText(settingData.getAboutApp());
                    SaveData(getActivity(),"app_phone_number",settingData.getPhone());
                    SaveData(getActivity(),"app_email",settingData.getEmail());
                    SaveData(getActivity(),"facebook_uri",settingData.getFacebookUrl());
                    SaveData(getActivity(),"instagram_uri",settingData.getInstagramUrl());
                    SaveData(getActivity(),"twitter_uri",settingData.getTwitterUrl());
                    SaveData(getActivity(),"youtube_uri",settingData.getYoutubeUrl());

                }catch (Exception e){
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
