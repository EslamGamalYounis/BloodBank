package com.example.mybloodyapp.view.fragment.loginCycleFragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.newPassword.NewPassword;
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
public class ForgetPasswordFragmentTwo extends BaseFragment {


    EditText etForgetPasswordTwoVerificationCode;
    EditText etForgetPasswordTwoNewPassword;
    EditText etForgetPasswordTwoReNewPassword;

    Button buForgetFragmentTwoChange;

    public ForgetPasswordFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forget_password_two, container, false);
        etForgetPasswordTwoVerificationCode =view.findViewById(R.id.et_forget_password_two_verification_code);
        etForgetPasswordTwoNewPassword =view.findViewById(R.id.et_forget_password_two_new_password);
        etForgetPasswordTwoReNewPassword =view.findViewById(R.id.et_forget_password_two_renew_password);
        buForgetFragmentTwoChange =view.findViewById(R.id.bu_forget_fragment_two_change);
        //setUpActivity();

        Bundle bundle =getArguments();
        String verificationCode =bundle.getString("verificationCode");
        final String phone =bundle.getString("phone");

        etForgetPasswordTwoVerificationCode.setText(verificationCode);

        buForgetFragmentTwoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etForgetPasswordTwoNewPassword.getText().toString()==etForgetPasswordTwoReNewPassword.getText().toString()){
                getClient().setNewPassword(
                        etForgetPasswordTwoNewPassword.getText().toString(),
                        etForgetPasswordTwoReNewPassword.getText().toString(),
                        etForgetPasswordTwoVerificationCode.getText().toString(),
                        phone).enqueue(new Callback<NewPassword>() {
                    @Override
                    public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(getContext(), "password was changed successfully  ", Toast.LENGTH_LONG).show();
                                Intent intent =new Intent(getActivity(), HomeCycleActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getContext(),"the phone number or password that you enter it may be wrong"
                                                +"\nplease enter a valid phone number with a valid password"
                                        ,Toast.LENGTH_LONG).show();                            }

                        }catch (Exception e){
                            Log.i("eslam2",e.getMessage());
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewPassword> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
                else {
                    Toast.makeText(getContext(), "the two password must be th same", Toast.LENGTH_LONG).show();
                }
            }

        });
        return view;
    }

}
