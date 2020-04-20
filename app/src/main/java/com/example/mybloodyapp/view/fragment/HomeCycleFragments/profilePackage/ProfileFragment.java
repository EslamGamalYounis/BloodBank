package com.example.mybloodyapp.view.fragment.HomeCycleFragments.profilePackage;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.profile.Profile;
import com.example.mybloodyapp.data.model.profile.ProfileData;
import com.example.mybloodyapp.helper.DataTxt;
import com.example.mybloodyapp.helper.HelperMethod;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;


import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */


public class ProfileFragment extends BaseFragment {

    private String API_TOKEN;
    @BindView(R.id.et_profile_name)
    EditText etProfileName;
    @BindView(R.id.et_profile_email)
    EditText etProfileEmail;
    @BindView(R.id.txt_profile_day_of_birth)
    TextView txtProfileDayOfBirth;
    @BindView(R.id.bu_calender_day_of_birth)
    Button buCalenderDayOfBirth;
    @BindView(R.id.spinner_profile_blood_type)
    Spinner spinnerProfileBloodType;
    @BindView(R.id.txt_profile_last_donation)
    TextView txtProfileLastDayDonation;
    @BindView(R.id.bu_profile_last_date_of_donation)
    Button buProfileLastDateOfDonation;
    @BindView(R.id.spinner_profile_governurate)
    Spinner spinnerProfileGovernurate;
    @BindView(R.id.spinner_profile_city)
    Spinner spinnerProfileCity;
    @BindView(R.id.et_profile_phone)
    EditText etProfilePhone;
    @BindView(R.id.et_profile_password)
    EditText etProfilePassword;
    @BindView(R.id.et_profile_password_again)
    EditText etProfilePasswordAgain;
    @BindView(R.id.bu_profile_edit)
    Button buProfileEdit;

    ProfileData profileData;
    private EmptySpinnerAdapter spinnerGovernoratesAdapter;
    private EmptySpinnerAdapter spinnerBloodTypeAdapter;
    private EmptySpinnerAdapter spinnerCityAdapter;
    Calendar calendar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        //Inflate the layout for this fragment

        API_TOKEN =LoadData(getActivity(),"API_TOKEN");
        setSpinnerGovernoratesData();
        setSpinnerBloodTypeData();

        getProfileDataFromApi();

        //setProfileDataFromSharedPrefrences();

        calendar = Calendar.getInstance();
        buCalenderDayOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.disappearKeypad(getActivity(),v);
                HelperMethod.showCalender(getActivity(),getString(R.string.day_of_birth),
                        txtProfileDayOfBirth
                        ,new DataTxt(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                                String.valueOf(calendar.get(Calendar.MONTH)),
                                String.valueOf(calendar.get(Calendar.YEAR)),"dd-MMMM-yyyy"));
            }
        });

        buProfileLastDateOfDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.disappearKeypad(getActivity(),v);
                HelperMethod.showCalender(getActivity(),getString(R.string.day_of_birth),
                        txtProfileLastDayDonation
                        ,new DataTxt(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                                String.valueOf(calendar.get(Calendar.MONTH)),
                                String.valueOf(calendar.get(Calendar.YEAR)),"dd-MMMM-yyyy"));
            }
        });

        buProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        return view;
    }


    public void validation(){
        if (etProfileName.getText()==null&&
            etProfileEmail.getText()==null&&
            spinnerBloodTypeAdapter.selectedId==0&&
            txtProfileDayOfBirth.getText()==null&&
            spinnerGovernoratesAdapter.selectedId==0&&
            spinnerCityAdapter.selectedId==0&&
            etProfilePhone.getText()==null&&
            etProfilePassword==null&&
            etProfilePasswordAgain.getText()==null)
        {
            return;
        }
        else{
            editProfile();
        }
    }

    public void editProfile(){
        getClient().editProfile(etProfileName.getText().toString(),
                                etProfileEmail.getText().toString(),
                                txtProfileDayOfBirth.getText().toString(),
                                String.valueOf(spinnerCityAdapter.selectedId),
                                etProfilePhone.getText().toString(),
                                txtProfileLastDayDonation.getText().toString(),
                                etProfilePassword.getText().toString(),
                                etProfilePasswordAgain.getText().toString(),
                                String.valueOf(spinnerBloodTypeAdapter.selectedId),
                                API_TOKEN).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }

    public void setProfileDataFromSharedPrefrences() {
        etProfileName.setText(LoadData(getActivity(),"user_name"));
        etProfileEmail.setText(LoadData(getActivity(),"account_email"));
        txtProfileDayOfBirth.setText(LoadData(getActivity(),"birthday_date"));
        spinnerProfileBloodType.setSelection(Integer.valueOf(LoadData(getActivity(),"bloodType_id")));
        txtProfileLastDayDonation.setText(LoadData(getActivity(),"donationLast_data"));
        spinnerProfileGovernurate.setSelection((Integer.valueOf(LoadData(getActivity(),"governorate_id"))));
        spinnerProfileCity.setSelection(Integer.valueOf((LoadData(getActivity(),"city_id"))));
        etProfilePhone.setText(LoadData(getActivity(),"phone_number"));
        etProfilePassword.setText(LoadData(getActivity(),"account_password"));
        etProfilePasswordAgain.setText(LoadData(getActivity(),"account_password"));
    }

    public void getProfileDataFromApi(){
        getClient().getProfileData(API_TOKEN).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                try {
                    if (response.body().getStatus()==1) {
                        profileData =response.body().getData();
                        setProfileData(profileData);
                    }

                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setProfileData(ProfileData profileData) {
        etProfileName.setText(profileData.getClient().getName());
        etProfileEmail.setText(profileData.getClient().getEmail());
        txtProfileDayOfBirth.setText(profileData.getClient().getBirthDate());
        spinnerProfileBloodType.setSelection(Integer.valueOf(profileData.getClient().getBloodTypeId()));
        txtProfileLastDayDonation.setText(profileData.getClient().getDonationLastDate());
        spinnerProfileGovernurate.setSelection((Integer.valueOf(profileData.getClient().getCity().getGovernorateId())));
        spinnerProfileCity.setSelection(Integer.valueOf(profileData.getClient().getCity().getId()));
        etProfilePhone.setText(profileData.getClient().getPhone());
        etProfilePassword.setText(LoadData(getActivity(),"account_password"));
        etProfilePasswordAgain.setText(LoadData(getActivity(),"account_password"));

    }


    public void setSpinnerGovernoratesData(){
        getClient().getGovernorates().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus()==1){
                        spinnerGovernoratesAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerGovernoratesAdapter.setData(response.body().getData(),getString(R.string.select_governorates));
                        spinnerProfileGovernurate.setAdapter(spinnerGovernoratesAdapter);
                        spinnerProfileGovernurate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                setSpinnerCityData(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                setSpinnerCityData(1);
                            }
                        });
                    }
                }
                catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSpinnerCityData(int governorateId){
        getClient().getCity(governorateId).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus()==1){
                        spinnerCityAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerCityAdapter.setData(response.body().getData(),getString(R.string.select_city));
                        spinnerProfileCity.setAdapter(spinnerCityAdapter);
                    }
                }
                catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSpinnerBloodTypeData(){
        getClient().getBloodType().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus()==1){
                        spinnerBloodTypeAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerBloodTypeAdapter.setData(response.body().getData(),getString(R.string.select_blood_type));
                        spinnerProfileBloodType.setAdapter(spinnerBloodTypeAdapter);
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
