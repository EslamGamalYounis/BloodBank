package com.example.mybloodyapp.view.fragment.HomeCycleFragments.donationRequest;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.createDonation.CreateDonation;
import com.example.mybloodyapp.data.model.donations.Donations;
import com.example.mybloodyapp.helper.GPSTracker;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.mapFragment.MapFragment;

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
public class AddingDontationRequestFragment extends BaseFragment implements MapFragment.GetLocationListener {

    EmptySpinnerAdapter spinnerGovernoratesAdapter;
    EmptySpinnerAdapter spinnerBloodTypeAdapter;
    EmptySpinnerAdapter spinnerCityAdapter;
    double currentLatitude;
    double currentLongitude;
    String location_details;

    double mHospitalLatitude;
    double mHospitalLongitude;
    String mHospital_location_details;

    public String API_TOKEN;

    @BindView(R.id.et_adding_donation_name)
    EditText etAddingDonationName;
    @BindView(R.id.et_adding_donation_age)
    EditText etAddingDonationAge;
    @BindView(R.id.spinner_adding_donation_blood_type)
    Spinner spinnerAddingDonationBloodType;
    @BindView(R.id.et_adding_donation_number_of_bags)
    EditText etAddingDonationNumberOfBags;
    @BindView(R.id.et_adding_donation_hospital_name)
    EditText etAddingDonationHospitalName;
    @BindView(R.id.bu_adding_donation_open_map)
    Button buAddingDonationOpenMap;
    @BindView(R.id.et_adding_donation_hospital_address)
    EditText etAddingDonationHospitalAddress;
    @BindView(R.id.spinner_adding_donation_governorate)
    Spinner spinnerAddingDonationGovernorate;
    @BindView(R.id.spinner_adding_donation_city)
    Spinner spinnerAddingDonationCity;
    @BindView(R.id.et_adding_donation_phone)
    EditText etAddingDonationPhone;
    @BindView(R.id.et_adding_donation_notes)
    EditText etAddingDonationNotes;
    @BindView(R.id.bu_adding_donation_send)
    Button buAddingDonationSend;

    public AddingDontationRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adding_dontation_request, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment
        API_TOKEN =LoadData(getActivity(),"API_TOKEN");

        setSpinnerBloodTypeData();
        setSpinnerGovernoratesData();

        GPSTracker gpsTracker =new GPSTracker(getContext(),getActivity());
        gpsTracker.getLocation();
        currentLatitude =gpsTracker.getLatitude();
        currentLongitude =gpsTracker.getLongitude();
        location_details =gpsTracker.getAddressLine(getContext());




        final Bundle bundle =new Bundle();
        bundle.putString("currentLatitude",String.valueOf(currentLatitude));
        bundle.putString("currentLongitude",String.valueOf(currentLongitude));
        bundle.putString("location_details",location_details);

        buAddingDonationOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapFragment mapFragment =new MapFragment();
                mapFragment.setArguments(bundle);
                mapFragment.setRef(AddingDontationRequestFragment.this);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.frame_of_home_fragment_container, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

       buAddingDonationSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDonation();
                Intent intent =new Intent(getActivity(), HomeCycleActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



    public void addDonation() {
        getClient().addDontationRequest(API_TOKEN,
                etAddingDonationName.getText().toString(),
                etAddingDonationAge.getText().toString(),
                spinnerBloodTypeAdapter.selectedId,
                etAddingDonationNumberOfBags.getText().toString(),
                etAddingDonationHospitalName.getText().toString(),
                etAddingDonationHospitalAddress.getText().toString(),
                spinnerCityAdapter.selectedId,
                etAddingDonationPhone.getText().toString(),
                etAddingDonationNotes.getText().toString(),
                mHospitalLatitude, mHospitalLongitude).enqueue(new Callback<CreateDonation>() {
            @Override
            public void onResponse(Call<CreateDonation> call, Response<CreateDonation> response) {
                try {
                    if (response.body().getStatus()==1){
                        Toast.makeText(getActivity(),getString(R.string.adding_request_is_done) , Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CreateDonation> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void setSpinnerGovernoratesData() {
        getClient().getGovernorates().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerGovernoratesAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerGovernoratesAdapter.setData(response.body().getData(), getString(R.string.select_governorates));
                        spinnerAddingDonationGovernorate.setAdapter(spinnerGovernoratesAdapter);
                        spinnerAddingDonationGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setSpinnerCityData(int governorateId) {
        getClient().getCity(governorateId).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerCityAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerCityAdapter.setData(response.body().getData(), getString(R.string.select_city));
                        spinnerAddingDonationCity.setAdapter(spinnerCityAdapter);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setSpinnerBloodTypeData() {
        getClient().getBloodType().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        spinnerBloodTypeAdapter = new EmptySpinnerAdapter(getActivity());
                        spinnerBloodTypeAdapter.setData(response.body().getData(), getString(R.string.select_blood_type));
                        spinnerAddingDonationBloodType.setAdapter(spinnerBloodTypeAdapter);
                    }
                } catch (Exception e) {
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


    @Override
    public void getLocationSend(double hospitalLatitude, double hospitalLongitude, String fullLocationDetails) {
        this.mHospitalLatitude=hospitalLongitude;
        this.mHospitalLongitude=hospitalLongitude;
        this.mHospital_location_details=fullLocationDetails;
        etAddingDonationHospitalAddress.setText(fullLocationDetails);
        Log.i("llllllllllllll",fullLocationDetails);
    }
}
