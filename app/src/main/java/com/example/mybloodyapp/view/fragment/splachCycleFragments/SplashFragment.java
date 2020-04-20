package com.example.mybloodyapp.view.fragment.splachCycleFragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.example.mybloodyapp.view.fragment.loginCycleFragment.LoginFragment;



public class SplashFragment extends BaseFragment {
    private static int SPLASH_TIME_OUT =2000;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpActivity();
        //ButterKnife.bind(this,view);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.fragment_splash,container,false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    SliderFragment sliderFragment = new SliderFragment();
                    //i am change fragmentManger() to getActivity().getSupportFragmentManager();
                    // because is deprecated in 28
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_splash_container,sliderFragment).addToBackStack(null).commit();
                }catch (Exception ex){
                    Log.i("kkkkkk", ex.getMessage().toString());
                }
            }
        },SPLASH_TIME_OUT);        return view;
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

}
