package com.example.mybloodyapp.view.fragment.mapFragment;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.donationRequest.AddingDontationRequestFragment;
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

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    View mView;

    Marker marker;
    String fullLocationDetails;
    @BindView(R.id.map_fragment_map_view)
    MapView mapFragmentMapView;
    @BindView(R.id.bu_map_fragment_location)
    Button buMapFragmentLocation;
    private double myLatitude;
    private double myLongitude;

    private double hospitalLatitude;
    private double hospitalLongitude;

    private String location_details;
    private Marker myLocationMarker;

    public AddingDontationRequestFragment addingDontationRequestFragment;


    public MapFragment() {
        // Required empty public constructor
    }

    public interface GetLocationListener {
        public void getLocationSend(double hospitalLatitude,double hospitalLongitude,String fullLocationDetails);
    }


    public void setRef(AddingDontationRequestFragment addingDontationRequestFragment){
        this.addingDontationRequestFragment = addingDontationRequestFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        String latitudeString = bundle.getString("currentLatitude");
        String longitudeString = bundle.getString("currentLongitude");
        location_details= bundle.getString("location_details");

        myLatitude = Double.valueOf(latitudeString);
        myLongitude = Double.valueOf(longitudeString);

        return view;
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
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        myLocationMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(myLatitude, myLongitude)).title("my current location "+location_details).snippet("i hope its a good day"));
        CameraPosition mylocation = CameraPosition.builder().target(new LatLng(myLatitude, myLongitude)).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mylocation));

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                double latitude = latLng.latitude;
                double longtiude = latLng.longitude;
                if (marker != null) {
                    marker.remove();
                }
                marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longtiude)).
                        title("my hospital location").snippet("please i need your help "));
                CameraPosition hospital = CameraPosition.builder().target(new LatLng(latitude, longtiude)).zoom(16).bearing(0).tilt(45).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(hospital));
            }
        });

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                fullLocationDetails = getAddress(getContext(), marker.getPosition().latitude, marker.getPosition().longitude);
                return false;
            }
        });

        buMapFragmentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hospitalLatitude = marker.getPosition().latitude;
                hospitalLongitude =marker.getPosition().longitude;

                GetLocationListener getLocationListener =(GetLocationListener) addingDontationRequestFragment;

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.remove(MapFragment.this);
                fragmentTransaction.addToBackStack(null);
                getLocationListener.getLocationSend(hospitalLatitude,hospitalLongitude,fullLocationDetails);
                fragmentTransaction.commit();
            }
        });
    }

    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {

            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);

            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            return add;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }


    @Override
    public void onBack() {
       super.onBack();
    }
}
