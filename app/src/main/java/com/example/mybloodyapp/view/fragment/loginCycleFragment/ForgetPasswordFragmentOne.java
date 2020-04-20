package com.example.mybloodyapp.view.fragment.loginCycleFragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.resetPassword.ResetPassword;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPasswordFragmentOne extends BaseFragment {

    EditText etForgetPasswordOnePhoneNumber;
    Button buForgetFragmentOneSend;
    public ForgetPasswordFragmentOne() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password_one, container, false);
        setUpActivity();

        etForgetPasswordOnePhoneNumber =view.findViewById(R.id.et_forget_password_one_phone_number);
        buForgetFragmentOneSend =view.findViewById(R.id.bu_forget_fragment_one_send);

        buForgetFragmentOneSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClient().setResetPassword(etForgetPasswordOnePhoneNumber.getText().toString()).enqueue(new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                String verificationCode =response.body().getData().getPinCodeForTest().toString();
                                String phone =etForgetPasswordOnePhoneNumber.getText().toString();
                                Toast.makeText(getContext(), "verification code was sent  ", Toast.LENGTH_LONG).show();
                                ForgetPasswordFragmentTwo forgetPasswordFragmentTwo = new ForgetPasswordFragmentTwo();
                                Bundle bundle =new Bundle();
                                bundle.putString("verificationCode",verificationCode);
                                bundle.putString("phone",phone);
                                forgetPasswordFragmentTwo.setArguments(bundle);
                                FragmentTransaction fragmentTransactionThree = getFragmentManager().beginTransaction();
                                fragmentTransactionThree.replace(R.id.fragment_login_container,
                                        forgetPasswordFragmentTwo, null).commit();
                            } else {
                                Toast.makeText(getContext(), "enter an right number", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i("eslam",e.getMessage());
                        }
                    }
                    @Override
                    public void onFailure (Call < ResetPassword > call, Throwable t){
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                      }
                });

                return view;
            }


    @Override
    public void onBack() {
        super.onBack();
    }
}
