package com.example.mybloodyapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.notification.NotificationData;
import com.example.mybloodyapp.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class

NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private List<NotificationData> notificationDataList = new ArrayList<>();

    public NotificationAdapter(Context context, Activity activity, List<NotificationData> notificationDataList) {
        this.context = context;
        this.activity = (BaseActivity) activity;
        this.notificationDataList = notificationDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        if (notificationDataList.get(position).getPivot().getIsRead().equals("0")) {
            holder.imageNotificationItem.setImageResource(R.drawable.ic_notifications_red);
        } else {
            holder.imageNotificationItem.setImageResource(R.drawable.ic_notifications_white);
        }

        holder.tvNotificationItemTitle.setText(notificationDataList.get(position).getTitle());
        holder.tvNotificationItemTime.setText(notificationDataList.get(position).getCreatedAt());

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_notification_item)
        ImageView imageNotificationItem;
        @BindView(R.id.tv_notification_item_title)
        TextView tvNotificationItemTitle;
        @BindView(R.id.tv_notification_item_time)
        TextView tvNotificationItemTime;
        @BindView(R.id.cardView_notification_item)
        CardView cardViewNotificationItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
