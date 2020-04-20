package com.example.mybloodyapp.view.fragment.loginCycleFragment;


import android.content.Intent;
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

import androidx.fragment.app.Fragment;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.city.CityData;
import com.example.mybloodyapp.data.model.login.Client;
import com.example.mybloodyapp.data.model.login.Login;
import com.example.mybloodyapp.helper.DataTxt;
import com.example.mybloodyapp.helper.HelperMethod;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.BloodTypeRquestData.getSpinnerBloodTypeData;
import static com.example.mybloodyapp.GeneralRequest.getSpinnerData;
import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewAccountFragment extends BaseFragment {

    EditText etRigsterFragmentName ;
    EditText etRigsterFragmentEmail ;
    TextView tvRigsterFragmentDayOfBirth;
    TextView tvRigsterFragmentLastTimeToDonation;
    EditText etRigsterFragmentPhoneNumber;
    EditText etRigsterFragmentPassword;
    EditText etRigsterFragmentConfirmPassword ;

    Button buCreateAccountFragmentRegister ;
    Spinner spinnerRigsterFragmentBloodType ;
    Spinner spinnerRigsterFragmentGovernorate ;
    Spinner spinnerRigsterFragmentCity ;


    EmptySpinnerAdapter spinnerGovernoratesAdapter ;
    EmptySpinnerAdapter spinnerBloodTypeAdapter;
    EmptySpinnerAdapter spinnerCityAdapter;

    Calendar calendar;

    public CreateNewAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_account, container, false);
       // ButterKnife.bind(this,view);
        setUpActivity();

        etRigsterFragmentName =view.findViewById(R.id.et_rigster_fragment_name) ;
        etRigsterFragmentEmail =view.findViewById(R.id.et_rigster_fragment_email) ;
        tvRigsterFragmentDayOfBirth= view.findViewById(R.id.tv_rigster_fragment_day_of_birth);
        tvRigsterFragmentLastTimeToDonation=view.findViewById(R.id.tv_rigster_fragment_last_time_to_donation);
        etRigsterFragmentPhoneNumber=view.findViewById(R.id.et_rigster_fragment_phone_number);
        etRigsterFragmentPassword=view.findViewById(R.id.et_rigster_fragment_password);
        etRigsterFragmentConfirmPassword =view.findViewById(R.id.et_rigster_fragment_confirm_password);

        buCreateAccountFragmentRegister =view.findViewById(R.id.bu_create_account_fragment_register);
        spinnerRigsterFragmentBloodType =view.findViewById(R.id.spinner_rigster_fragment_blood_type);
        spinnerRigsterFragmentGovernorate =view.findViewById(R.id.spinner_rigster_fragment_governorate);
        spinnerRigsterFragmentCity =view.findViewById(R.id.spinner_rigster_fragment_city);

        calendar = Calendar.getInstance();

        tvRigsterFragmentDayOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.showCalender(getActivity(),getString(R.string.day_of_birth),
                        tvRigsterFragmentDayOfBirth
                        ,new DataTxt(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                                String.valueOf(calendar.get(Calendar.MONTH)),
                                String.valueOf(calendar.get(Calendar.YEAR)),"dd-MMMM-yyyy"));
            }
        });

        tvRigsterFragmentLastTimeToDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethod.showCalender(getActivity(),getString(R.string.day_of_birth),
                        tvRigsterFragmentLastTimeToDonation
                        ,new DataTxt(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)),
                                String.valueOf(calendar.get(Calendar.MONTH)),
                                String.valueOf(calendar.get(Calendar.YEAR)),"dd-MMMM-yyyy"));
            }
        });

        setSpinnerGovernoratesData();
        setSpinnerBloodTypeData();

        buCreateAccountFragmentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRegister();
            }
        });

        return view;
    }

    public void setSpinnerGovernoratesData(){
            getClient().getGovernorates().enqueue(new Callback<City>() {
                @Override
                public void onResponse(Call<City> call, Response<City> response) {
                    try {
                        if (response.body().getStatus()==1){
                            spinnerGovernoratesAdapter = new EmptySpinnerAdapter(getActivity());
                            spinnerGovernoratesAdapter.setData(response.body().getData(),getString(R.string.select_governorates));
                            spinnerRigsterFragmentGovernorate.setAdapter(spinnerGovernoratesAdapter);
                            spinnerRigsterFragmentGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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
                        spinnerRigsterFragmentCity.setAdapter(spinnerCityAdapter);
                    }
                }
                catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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
                        spinnerRigsterFragmentBloodType.setAdapter(spinnerBloodTypeAdapter);
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postRegister() {
        if (etRigsterFragmentPhoneNumber.getText().toString().length()==11&&
                etRigsterFragmentEmail.getText().toString().contains("@")) {
            getClient().setRegister(etRigsterFragmentName.getText().toString(),
                    etRigsterFragmentEmail.getText().toString(),
                    tvRigsterFragmentDayOfBirth.getText().toString(),
                    String.valueOf(spinnerCityAdapter.selectedId),
                    etRigsterFragmentPhoneNumber.getText().toString(),
                    tvRigsterFragmentLastTimeToDonation.getText().toString(),
                    etRigsterFragmentPassword.getText().toString(),
                    etRigsterFragmentConfirmPassword.getText().toString(),
                    String.valueOf(spinnerBloodTypeAdapter.selectedId))
                    .enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, final Response<Login> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "gggggggg", Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(getActivity(), HomeCycleActivity.class);
                            startActivity(intent);
                        } else {
                            String msg=response.body().getMsg();
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onBack() {
        replaceFragment(getFragmentManager(),R.id.fragment_login_container,new LoginFragment());
    }


}


  /*@OnClick({R.id.spinner_rigster_fragment_blood_type, R.id.spinner_rigster_fragment_governorate,
            R.id.spinner_rigster_fragment_city, R.id.bu_create_account_fragment_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.spinner_rigster_fragment_blood_type:
                break;
            case R.id.spinner_rigster_fragment_governorate:
                break;
            case R.id.spinner_rigster_fragment_city:
                break;
            case R.id.bu_create_account_fragment_register:
                postRegister();
                break;
        }
    }*/

/*    private void postRegister() {
        getClient().SetRegister(etRigsterFragmentName.getText().toString(),etRigsterFragmentEmail.getText().toString(),
                etRigsterFragmentDayOfBirth.getText().toString(),String.valueOf(spinnerRigsterFragmentCity.getSelectedItemId()),
                etRigsterFragmentPhoneNumber.getText().toString(),etRigsterFragmentLastTimeToDonation.getText().toString(),
                etRigsterFragmentPassword.getText().toString(),etRigsterFragmentConfirmPassword.getText().toString(),
                 String.valueOf(spinnerRigsterFragmentBloodType.getSelectedItemId())).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, final Response<Register> response) {
                try {
                    if (response.body().getStatus()==1) {
                        setData(response.body().getData().getClient());

                        spinnerGovernoratesAdapter =new EmptySpinnerAdapter(getActivity());
                        spinnerBloodTypeAdapter =new EmptySpinnerAdapter(getActivity());
                        spinnerCityAdapter =new EmptySpinnerAdapter(getActivity());

                        getSpinnerData(getClient().getGovernorates(),spinnerRigsterFragmentGovernorate,
                                spinnerGovernoratesAdapter,response.body().getData().getClient().getBloodType().getId(),
                                getString(R.string.select_blood_type));

                        AdapterView.OnItemSelectedListener onBloodTypeItemSelected = new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (id!=0) {

                                    getSpinnerData(getClient().getBloodType(),spinnerRigsterFragmentCity,
                                            spinnerCityAdapter,response.body().getData().getClient().getCity().getId(),
                                            getString(R.string.select_city));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        };
                        getSpinnerData(getClient().getBloodType(),spinnerRigsterFragmentGovernorate,
                                spinnerGovernoratesAdapter,response.body().getData().getClient().getBloodType().getId(),
                                getString(R.string.select_governorates), onBloodTypeItemSelected);



                    }
                }catch (Exception ex)
                {
                    ex.getMessage();
                }
            }
            @Override
            public void onFailure(Call<Register> call, Throwable t) {
            }
        });
    }

    private void setData(Client client) {

    }*/
