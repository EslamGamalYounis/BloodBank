package com.example.mybloodyapp.adapter;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.donations.DonationsData;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.donationRequest.ShowOneDonationFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.ContextCompat.startActivity;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;

public class RecyclerViewAdapterDonations extends RecyclerView.Adapter<RecyclerViewAdapterDonations.DonationRowHolder> {
    private List<DonationsData> donationList;
    Context context;
    private String donationRequestIDString;
    private static final int REQUEST_CALL= 122;


    public RecyclerViewAdapterDonations(List<DonationsData> donationsList, Context context) {
        this.donationList = donationsList;
        this.context = context;
    }

    @NonNull
    @Override
    public DonationRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donation, null);
        DonationRowHolder donationRowHolder = new DonationRowHolder(view);
        return donationRowHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DonationRowHolder holder, int position) {
        holder.txtDonationItemNameOfPerson.setText(donationList.get(position).getPatientName());
        holder.txtDonationItemNameOfHospital.setText(donationList.get(position).getHospitalName());
        holder.txtDonationItemNameOfGovernorate.setText(donationList.get(position).getCity().getName());
        holder.imageDonationBloodType.setText(donationList.get(position).getBloodType().getName());
        donationRequestIDString = donationList.get(position).getId().toString();
        //donationRequestIDString =String.valueOf(donationList.get(holder.getAdapterPosition()).getId());

        holder.linearLayoutControlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("donationRequestIDString", donationRequestIDString);
                ShowOneDonationFragment showOneDonationFragment = new ShowOneDonationFragment();
                showOneDonationFragment.setArguments(bundle);
                HomeCycleActivity homeCycleActivity = (HomeCycleActivity) context;
                replaceFragment(homeCycleActivity.getSupportFragmentManager(),
                        R.id.frame_of_home_fragment_container, showOneDonationFragment);
            }
        });

        holder.buImgPhoneCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = donationList.get(holder.getAdapterPosition()).getPhone();
                String dial = "tel:" + phoneNumber;
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    requestCallPermission();
                }else {
                    context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            }
        });
    }

    private void requestCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.CALL_PHONE)){
            new AlertDialog.Builder(context)
                    .setTitle("permission needed")
                    .setMessage("this permission is needed because of you can not call any patient")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
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
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
    }




    @Override
    public int getItemCount() {
        return donationList.size();
    }


    public class DonationRowHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bu_img_phone_call_button)
        ImageButton buImgPhoneCallButton;
        @BindView(R.id.bu_img_info_button)
        ImageButton buImgInfoButton;
        @BindView(R.id.txt_donation_item_name_of_person)
        TextView txtDonationItemNameOfPerson;
        @BindView(R.id.txt_donation_item_person_name)
        TextView txtDonationItemPersonName;
        @BindView(R.id.txt_donation_item_name_of_hospital)
        TextView txtDonationItemNameOfHospital;
        @BindView(R.id.txt_donation_item_hospital_name)
        TextView txtDonationItemHospitalName;
        @BindView(R.id.layout_hospital_name)
        LinearLayout layoutHospitalName;
        @BindView(R.id.txt_donation_item_name_of_governorate)
        TextView txtDonationItemNameOfGovernorate;
        @BindView(R.id.txt_donation_item_governorate_name)
        TextView txtDonationItemGovernorateName;
        @BindView(R.id.image_donation_blood_type)
        TextView imageDonationBloodType;
        @BindView(R.id.cardView_item_donation_home_nav)
        CardView cardViewItemDonationHomeNav;
        @BindView(R.id.linear_layout_control_click)
        LinearLayout linearLayoutControlClick;

        public DonationRowHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
