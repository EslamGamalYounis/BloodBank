package com.example.mybloodyapp.view.fragment.loginCycleFragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.login.Login;
import com.example.mybloodyapp.data.model.login.LoginData;
import com.example.mybloodyapp.helper.SharedPreferencesManger;
import com.example.mybloodyapp.view.activity.HomeCycleActivity;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.SaveData;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    Button buLoginFragmentLogin;
    Button buLoginFragmentSignUp;
    TextView txtLoginFragmentDoYouForget;
    EditText etLoginFragmentPhoneNumber;
    EditText etLoginFragmentPassword;
    @BindView(R.id.checkBox_login_fragment)
    CheckBox checkBoxLoginFragment;
    private String phoneNumber;
    private String password;
    LoginData loginData;


    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setUpActivity();
        ButterKnife.bind(this,view);

        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("Checkbox",MODE_PRIVATE);
        boolean checkbox= sharedPreferences.getBoolean("remember",false);
        if (checkbox==true)
        {
            Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
            startActivity(intent);
        }else if (checkbox ==false)
        {
            Toast.makeText(getActivity(),"please sign in",Toast.LENGTH_SHORT).show();
        }

        txtLoginFragmentDoYouForget = view.findViewById(R.id.txt_login_fragment_doYouForget);
        buLoginFragmentLogin = view.findViewById(R.id.bu_login_fragment_login);
        buLoginFragmentSignUp = view.findViewById(R.id.bu_login_fragment_sign_up);

        etLoginFragmentPhoneNumber = view.findViewById(R.id.et_login_fragment_phone_number);
        etLoginFragmentPassword = view.findViewById(R.id.et_login_fragment_password);

        txtLoginFragmentDoYouForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordFragmentOne forgetPasswordFragmentOne = new ForgetPasswordFragmentOne();
                FragmentTransaction fragmentTransactionOne = getFragmentManager().beginTransaction();
                fragmentTransactionOne.replace(R.id.fragment_login_container,
                        forgetPasswordFragmentOne).addToBackStack(null).commit();
            }
        });

        buLoginFragmentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccountFragment createNewAccountFragment = new CreateNewAccountFragment();
                FragmentTransaction fragmentTransactionThree = getFragmentManager().beginTransaction();
                fragmentTransactionThree.replace(R.id.fragment_login_container,
                        createNewAccountFragment, null).commit();
            }
        });

        checkBoxLoginFragment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()){
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("remember",true);
                    editor.apply();
                    Toast.makeText(getActivity(),"checked",Toast.LENGTH_SHORT).show();
                }
                else if (!buttonView.isChecked())
                {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("remember",false);
                    editor.apply();
                    Toast.makeText(getActivity(),"un checked",Toast.LENGTH_SHORT).show();

                }
            }
        });

        buLoginFragmentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getClient().setLogin(
                        etLoginFragmentPhoneNumber.getText().toString(),
                        etLoginFragmentPassword.getText().toString())
                        .enqueue(new Callback<Login>() {
                            @Override
                            public void onResponse(Call<Login> call, Response<Login> response) {
                                try {
                                    if (response.body().getStatus() == 1) {
                                        phoneNumber = etLoginFragmentPhoneNumber.getText().toString();
                                        password=  etLoginFragmentPassword.getText().toString();
                                        loginData =response.body().getData();
                                        SaveData(getActivity(),"phone_number",phoneNumber);
                                        SaveData(getActivity(),"account_password",password);

                                        //get Profile NotificationsSettingsData
                                        String name =loginData.getClient().getName();
                                        String email =loginData.getClient().getEmail();
                                        String birhtdayDate =loginData.getClient().getBirthDate();
                                        String bloodTypeId =loginData.getClient().getBloodTypeId();
                                        String donationLastDate =loginData.getClient().getDonationLastDate();
                                        String governorateId =loginData.getClient().getCity().getGovernorateId();
                                        String cityId =loginData.getClient().getCityId();
                                        String API_TOKEN =loginData.getApiToken();
                                        SaveData(getActivity(),"user_name",name);
                                        SaveData(getActivity(),"account_email",email);
                                        SaveData(getActivity(),"birthday_date",birhtdayDate);
                                        SaveData(getActivity(),"bloodType_id",bloodTypeId);
                                        SaveData(getActivity(),"donationLast_data",donationLastDate);
                                        SaveData(getActivity(),"governorate_id",governorateId);
                                        SaveData(getActivity(),"city_id",cityId);
                                        SaveData(getActivity(),"API_TOKEN",API_TOKEN);

                                        //start home activity
                                        Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getContext(), "welcome in home ", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "the phone number or password that you enter it may be wrong"
                                                        + "\nplease enter a valid phone number with a valid password"
                                                , Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Login> call, Throwable t) {
                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        return view;
    }


    @Override
    public void onBack() {
        getActivity().finish();
    }

}

  /*String doYouForgetPassword = getResources().getString(R.string.do_you_forget_password);
        SpannableString doYouForgetPassword_spannable =new SpannableString(doYouForgetPassword);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget){
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_login_container, new ForgetPasswordFragmentOne(), "ForgetPasswordFragmentOne");
                ft.addToBackStack(null);
                ft.commit();
            }
        };
        doYouForgetPassword_spannable.setSpan(clickableSpan1,0,doYouForgetPassword.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtLoginFragmentDoYouForget.setText(doYouForgetPassword_spannable);
        txtLoginFragmentDoYouForget.setMovementMethod(LinkMovementMethod.getInstance());*/
