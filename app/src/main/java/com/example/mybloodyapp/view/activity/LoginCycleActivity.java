package com.example.mybloodyapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.view.fragment.loginCycleFragment.ForgetPasswordFragmentOne;
import com.example.mybloodyapp.view.fragment.loginCycleFragment.LoginFragment;
import com.example.mybloodyapp.view.fragment.splachCycleFragments.SliderFragment;
import com.example.mybloodyapp.view.fragment.splachCycleFragments.SplashFragment;

public class LoginCycleActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cycle);

        if (findViewById(R.id.fragment_login_container)!=null){

            if (savedInstanceState !=null){
                return;
            }
            LoginFragment loginFragment =new LoginFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_login_container, loginFragment,null).commit();
        }
    }

}
