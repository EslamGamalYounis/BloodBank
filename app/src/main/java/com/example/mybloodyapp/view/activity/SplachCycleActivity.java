package com.example.mybloodyapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.view.fragment.splachCycleFragments.SliderFragment;
import com.example.mybloodyapp.view.fragment.splachCycleFragments.SplashFragment;

public class SplachCycleActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_cycle);

        if (findViewById(R.id.fragment_splash_container)!=null){

            if (savedInstanceState !=null){
                return;
            }
            SplashFragment splashFragment =new SplashFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_splash_container, splashFragment,null).commit();
        }
    }

}
