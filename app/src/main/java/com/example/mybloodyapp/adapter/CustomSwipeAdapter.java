package com.example.mybloodyapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.view.activity.LoginCycleActivity;


public class CustomSwipeAdapter extends PagerAdapter {
    ImageView imageSwipeLayout;
    ImageView imageView_button_text;
    ImageButton image_button;

    private int[] image_resources = {R.drawable.ic_slider_one, R.drawable.ic_slider_two};
    private int[] image_resources_text_image = {R.drawable.text_on_spinner, R.drawable.text_on_spinner};
    private int[] image_resources_forButtonSlider = {R.drawable.arrow, R.drawable.check};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context context) {
        this.context = context;

    }


    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);

        imageSwipeLayout =(ImageView)item_view.findViewById(R.id.image_swipe_layout);
        imageView_button_text =(ImageView)item_view.findViewById(R.id.image_fragment_slider_between);
        image_button =(ImageButton) item_view.findViewById(R.id.image_fragment_slider_button);

        imageSwipeLayout.setImageResource(image_resources[position]);
        imageView_button_text.setImageResource(image_resources_text_image[position]);
        image_button.setImageResource(image_resources_forButtonSlider[position]);

        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, LoginCycleActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
