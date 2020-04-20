package com.example.mybloodyapp.view.fragment.HomeCycleFragments.notificationsPackage;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.NotificationAdapter;
import com.example.mybloodyapp.data.model.notification.Notification;
import com.example.mybloodyapp.data.model.notification.NotificationData;
import com.example.mybloodyapp.helper.OnEndLess;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

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
public class NotificationFragment extends BaseFragment {


    private  String API_TOKEN  ;
    @BindView(R.id.img_bu_notification_back)
    ImageButton imgBuNotificationBack;
    @BindView(R.id.recycler_notifications)
    RecyclerView recyclerNotifications;
    private LinearLayoutManager linearLayoutManager;
    private NotificationAdapter notificationAdapter;
    private List<NotificationData> notificationDataList =new ArrayList<>();
    private int maxPage=0;
    private OnEndLess onEndLess;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        setUpActivity();
        ButterKnife.bind(this,view);
        // Inflate the layout for this fragment
        API_TOKEN =LoadData(getActivity(),"API_TOKEN");
        init();

        imgBuNotificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });
        return view;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerNotifications.setLayoutManager(linearLayoutManager);

        onEndLess =new OnEndLess(linearLayoutManager,1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getNotification(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        recyclerNotifications.addOnScrollListener(onEndLess);

        notificationAdapter =new NotificationAdapter(getActivity(),getActivity(),notificationDataList);
        recyclerNotifications.setAdapter(notificationAdapter);

        getNotification(1);
    }

    private void getNotification(int page) {
        getClient().getNotificationData(API_TOKEN,page).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                try {
                    if (response.body().getStatus()==1) {
                        maxPage =response.body().getData().getLastPage();
                        notificationDataList.addAll(response.body().getData().getData());
                        notificationAdapter.notifyDataSetChanged();
                    }


                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {
        Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
        startActivity(intent);
    }
}
