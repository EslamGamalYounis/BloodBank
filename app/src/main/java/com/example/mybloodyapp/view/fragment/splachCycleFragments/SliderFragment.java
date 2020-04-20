package com.example.mybloodyapp.view.fragment.splachCycleFragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.CustomSwipeAdapter;
import com.example.mybloodyapp.view.activity.SplachCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SliderFragment extends BaseFragment {
    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_slider, container, false);
        setUpActivity();
        //ButterKnife.bind(this,view);
        viewPager = view.findViewById(R.id.view_pager);

        customSwipeAdapter =new CustomSwipeAdapter(getActivity());
        viewPager.setAdapter(customSwipeAdapter);
        // Inflate the layout for this fragment
        return view ;

    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}
