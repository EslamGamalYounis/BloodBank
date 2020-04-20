package com.example.mybloodyapp.view.fragment.HomeCycleFragments.donationRequest;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.donationDetails.DonationDetails;
import com.example.mybloodyapp.data.model.donationDetails.DonationDetailsData;
import com.example.mybloodyapp.data.model.donations.Donations;
import com.example.mybloodyapp.data.model.donations.DonationsData;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.mapFragment.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadBoolean;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOneDonationFragment extends BaseFragment  implements OnMapReadyCallback {

    private static final int REQUEST_CALL = 122;
    private String API_TOKEN ;
    private static final String TAG = "ShowOneDonationFragment";
    DonationDetailsData donationsData ;
    @BindView(R.id.txt_donation_details_name_of_person_layout)
    TextView txtDonationDetailsNameOfPersonLayout;
    @BindView(R.id.img_bu_donation_details_back)
    ImageButton imgBuDonationDetailsBack;
    @BindView(R.id.txt_donation_details_name_of_person)
    TextView txtDonationDetailsNameOfPerson;
    @BindView(R.id.txt_donation_details_age_of_person)
    TextView txtDonationDetailsAgeOfPerson;
    @BindView(R.id.txt_donation_details_bloodType_of_person)
    TextView txtDonationDetailsBloodTypeOfPerson;
    @BindView(R.id.txt_donation_details_num_of_bags)
    TextView txtDonationDetailsNumOfBags;
    @BindView(R.id.txt_donation_details_name_of_hospital)
    TextView txtDonationDetailsNameOfHospital;
    @BindView(R.id.txt_donation_details_hospital_address)
    TextView txtDonationDetailsHospitalAddress;
    @BindView(R.id.txt_donation_details_phone_number)
    TextView txtDonationDetailsPhoneNumber;
    @BindView(R.id.map_container_layout)
    FrameLayout mapContainerLayout;
    @BindView(R.id.bu_donation_details_phone_call)
    Button buDonationDetailsPhoneCall;


    private String donationRequestIDString;
    private int donationRequestID;
    private String dial;
    private MapView mapFragmentMapView;
    GoogleMap mGoogleMap;
    private Marker myLocationMarker;
    private double myLatitude;
    private double myLongitude;
    private String location_details;

    public ShowOneDonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_one_donation, container, false);
        ButterKnife.bind(this, view);
        setUpActivity();
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        donationRequestIDString = bundle.getString("donationRequestIDString");
        API_TOKEN =LoadData(getActivity(),"API_TOKEN");

        //Toast.makeText(getActivity(), donationRequestIDString, Toast.LENGTH_LONG).show();
        donationRequestID= Integer.parseInt(donationRequestIDString);
        getDonationDetails(donationRequestID);




        imgBuDonationDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        buDonationDetailsPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = donationsData.getPhone();
                dial = "tel:" + phoneNumber;
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                    requestCallPermission();
                }
                else {
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            }
        });




        return view;
    }

    private void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE)){
            new AlertDialog.Builder(getActivity())
                    .setTitle("permission needed")
                    .setMessage("this permission is needed because of you can not call any patient")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length >0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"permission granted",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
            else {
                Toast.makeText(getActivity(),"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDonationDetails(int donationId) {
        getClient().getDonationDetails(API_TOKEN, donationId).enqueue(new Callback<DonationDetails>() {
            @Override
            public void onResponse(Call<DonationDetails> call, Response<DonationDetails> response) {
                if (response.body().getStatus() ==1){
                    donationsData =response.body().getData();
                    setData(donationsData);
                }
            }

            @Override
            public void onFailure(Call<DonationDetails> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setData(DonationDetailsData data) {
        txtDonationDetailsNameOfPersonLayout.setText(data.getPatientName());
        txtDonationDetailsNameOfPerson.setText(data.getPatientName());
        txtDonationDetailsAgeOfPerson.setText(data.getPatientAge());
        txtDonationDetailsBloodTypeOfPerson.setText(data.getBloodType().getName());
        txtDonationDetailsNumOfBags.setText(data.getBagsNum());
        txtDonationDetailsNameOfHospital.setText(data.getHospitalName());
        txtDonationDetailsHospitalAddress.setText(data.getHospitalAddress());
        txtDonationDetailsPhoneNumber.setText(data.getPhone());
        myLatitude = Double.parseDouble(data.getLatitude());
        myLongitude =Double.parseDouble(data.getLongitude());
        location_details=data.getHospitalAddress();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.map_container_layout);
        //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onBack() {
        Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapFragmentMapView != null) {
            mapFragmentMapView.onCreate(null);
            mapFragmentMapView.onResume();
            mapFragmentMapView.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        myLocationMarker = googleMap.addMarker(new MarkerOptions().
                position(new LatLng(myLatitude, myLongitude)).title("my current location "+location_details)
                .snippet("i hope its a good day"));
        CameraPosition mylocation = CameraPosition.builder().
                target(new LatLng(myLatitude, myLongitude)).zoom(10).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mylocation));
    }
}


