package com.example.mybloodyapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.notificationCount.NotificationCount;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.HomeContainerFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.morePackage.MoreSettingsFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.notificationsPackage.NotificationFragment;
import com.example.mybloodyapp.view.fragment.HomeCycleFragments.profilePackage.ProfileFragment;
import com.example.mybloodyapp.view.fragment.mapFragment.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

public class HomeCycleActivity extends BaseActivity {


    private Fragment selectedFagment;
    private String API_TOKEN ;
    int notfication_num;
    int notificationNumResult;

    private int getNotificationCountNum(){
        getClient().getNotificationCount(API_TOKEN).enqueue(new Callback<NotificationCount>() {
            @Override
            public void onResponse(Call<NotificationCount> call, Response<NotificationCount> response) {
                try {
                    if (response.body().getStatus()==1) {
                        notfication_num= response.body().getData().getNotificationsCount();
                        Log.d("notifiNum",String.valueOf(notfication_num));
                    }

                }catch (Exception e){
                    Log.d("notifiexp",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NotificationCount> call, Throwable t) {
                Log.d("notififail",t.getMessage());
            }
        });
        return notfication_num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);

        API_TOKEN = LoadData(this,"API_TOKEN");
        notificationNumResult = getNotificationCountNum();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        View chatBadge = LayoutInflater.from(this).inflate(R.layout.unread_message_layout,
                bottomNavigationView, false);
        BottomNavigationItemView itemView = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(2);
        TextView tvUnreadChats = chatBadge.findViewById(R.id.tvUnreadChats);
        if (notificationNumResult>0) {
            tvUnreadChats.setText(String.valueOf(notificationNumResult));
            itemView.addView(chatBadge);
        }




        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_of_home_fragment_container,
                new HomeContainerFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    selectedFagment =null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFagment =new HomeContainerFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFagment =new ProfileFragment();
                            break;

                        case R.id.nav_alarm_notification:
                            selectedFagment =new NotificationFragment();
                            break;

                        case R.id.nav_settings:
                            selectedFagment=new MoreSettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_of_home_fragment_container,
                            selectedFagment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        finishAffinity();
    }



}


