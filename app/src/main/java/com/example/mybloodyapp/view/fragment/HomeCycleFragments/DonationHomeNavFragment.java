package com.example.mybloodyapp.view.fragment.HomeCycleFragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.adapter.RecyclerViewAdapterDonations;
import com.example.mybloodyapp.data.model.city.City;
import com.example.mybloodyapp.data.model.donations.Donations;
import com.example.mybloodyapp.data.model.donations.DonationsData;
import com.example.mybloodyapp.helper.OnEndLess;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.donationRequest.AddingDontationRequestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadBoolean;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.SaveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationHomeNavFragment extends BaseFragment {

    EmptySpinnerAdapter SpinnerAdapterFragmentDonationBloodType;
    EmptySpinnerAdapter SpinnerAdapterFragmentDonationGovernurate;

    private String API_TOKEN ;
    private static final int REQUEST_CALL= 122;


    Context context;
    @BindView(R.id.spinner_fragment_donation_governorates)
    Spinner spinnerFragmentDonationGovernorates;
    @BindView(R.id.spinner_fragment_donation_blood_type)
    Spinner spinnerFragmentDonationBloodType;
    @BindView(R.id.recycler_donation_fragment)
    RecyclerView recyclerDonationFragment;
    @BindView(R.id.image_add_donation_home_nav)
    ImageButton imageAddDonationHomeNav;
    @BindView(R.id.bu_donation_fragment_search)
    Button buDonationFragmentSearch;
    private Integer maxPage = 0;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapterDonations recyclerViewAdapterDonations;
    private List<DonationsData> donationList = new ArrayList<>();
    private OnEndLess onEndLess;
    private boolean Filter=false;


    public DonationHomeNavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_nav_donation, container, false);
        ButterKnife.bind(this, view);

        SaveData(getActivity(),"PHONE_CALL_PERMISSION",false);

        imageAddDonationHomeNav.setImageResource(R.drawable.ic_add_red);
        context = getActivity();

        API_TOKEN =LoadData(getActivity(),"API_TOKEN");

        setSpinnerBloodTypeData();
        setSpinnerGovernoratesData();

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerDonationFragment.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        if (Filter){
                            onFilter(current_page);
                        }else{
                            getDonations(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        requestPermission();

        recyclerDonationFragment.addOnScrollListener(onEndLess);

        recyclerViewAdapterDonations = new RecyclerViewAdapterDonations(donationList, context);
        recyclerDonationFragment.setAdapter(recyclerViewAdapterDonations);

        getDonations(1);

        imageAddDonationHomeNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddingDontationRequestFragment addingDontationRequestFragment =new AddingDontationRequestFragment();
                FragmentTransaction fragmentTransaction =getParentFragment().getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_of_home_fragment_container,addingDontationRequestFragment,null).commit();
            }
        });

        buDonationFragmentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFilter(1);
            }
        });


        return view;
    }

    private void onFilter(int page){
        Filter =true;
        onEndLess.previousTotal=0;
        onEndLess.current_page =1;
        onEndLess.previous_page=1;
        donationList =new ArrayList<>();
        recyclerViewAdapterDonations =new RecyclerViewAdapterDonations(donationList,getActivity());
        recyclerDonationFragment.setAdapter(recyclerViewAdapterDonations);

        Call<Donations> call =getClient().getFilteredDonation(API_TOKEN,SpinnerAdapterFragmentDonationBloodType.selectedId,
                SpinnerAdapterFragmentDonationGovernurate.selectedId,page);
        getFilteredDonations(call,page);
    }

    private void requestPermission(){
        boolean permission =LoadBoolean(getActivity(),"PHONE_CALL_PERMISSION");
        if (permission==false) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(getActivity(), "you have already granted this permisson", Toast.LENGTH_SHORT).show();
            } else {
                requestCallPermission();
            }
        }
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
                SaveData(getActivity(),"PHONE_CALL_PERMISSION",true);
            }
            else {
                Toast.makeText(getActivity(),"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getDonations(int page) {
        getClient().getAllDonation(API_TOKEN, page).enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        donationList.addAll(response.body().getData().getData());
                        recyclerViewAdapterDonations.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getFilteredDonations(Call<Donations>call,int page){
        call.enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                try {
                    if (response.body().getStatus()==1) {
                        maxPage=response.body().getData().getLastPage();
                        donationList.addAll(response.body().getData().getData());
                        recyclerViewAdapterDonations.notifyDataSetChanged();
                    }
                }catch(Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {
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
                        SpinnerAdapterFragmentDonationBloodType = new EmptySpinnerAdapter(getActivity());
                        SpinnerAdapterFragmentDonationBloodType.setData(response.body().getData(), getString(R.string.select_blood_type));
                        spinnerFragmentDonationBloodType.setAdapter(SpinnerAdapterFragmentDonationBloodType);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                //Toast.makeText(getActivity(), "check your internet connection", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSpinnerGovernoratesData() {
        getClient().getGovernorates().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        SpinnerAdapterFragmentDonationGovernurate = new EmptySpinnerAdapter(getActivity());
                        SpinnerAdapterFragmentDonationGovernurate.setData(response.body().getData(), getString(R.string.select_governorates));
                        spinnerFragmentDonationGovernorates.setAdapter(SpinnerAdapterFragmentDonationGovernurate);
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

}








