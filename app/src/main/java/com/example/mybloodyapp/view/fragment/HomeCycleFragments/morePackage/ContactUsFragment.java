package com.example.mybloodyapp.view.fragment.HomeCycleFragments.morePackage;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;
import static com.example.mybloodyapp.helper.HelperMethod.replaceFragment;
import static com.example.mybloodyapp.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends BaseFragment {


    @BindView(R.id.img_bu_conact_us_back)
    ImageButton imgBuContactUsBack;
    @BindView(R.id.tv_contact_us_phone_number)
    TextView tvContactUsPhoneNumber;
    @BindView(R.id.tv_contact_us_email)
    TextView tvContactUsEmail;
    @BindView(R.id.image_contact_us_facebook)
    ImageView imageContactUsFacebook;
    @BindView(R.id.image_contact_us_instgram)
    ImageView imageContactUsInstgram;
    @BindView(R.id.image_contact_us_twitter)
    ImageView imageContactUsTwitter;
    @BindView(R.id.image_contact_us_youtube)
    ImageView imageContactUsYoutube;
    @BindView(R.id.et_contact_us_title_of_message)
    EditText etContactUsTitleOfMessage;
    @BindView(R.id.et_contact_us_body_of_message)
    EditText etContactUsBodyOfMessage;
    @BindView(R.id.bu_contact_us_send)
    Button buContactUsSend;

    private String API_TOKEN;
    private String facebookURL;
    private String instgramURL;
    private String twitterURL;
    private String youtubeURL;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

        API_TOKEN = LoadData(getActivity(),"API_TOKEN");
        String appPhoneNumber = LoadData(getActivity(),"app_phone_number");
        String app_email = LoadData(getActivity(),"app_email");
        tvContactUsPhoneNumber.setText(appPhoneNumber);
        tvContactUsEmail.setText(app_email);

        imgBuContactUsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        imageContactUsFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookURL =LoadData(getActivity(),"facebook_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL)));
                }
            }
        });

        imageContactUsInstgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instgramURL =LoadData(getActivity(),"instagram_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instgramURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instgramURL)));
                }
            }
        });

        imageContactUsTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterURL =LoadData(getActivity(),"twitter_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL)));
                }
            }
        });


        imageContactUsYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubeURL =LoadData(getActivity(),"youtube_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL)));
                }
            }
        });


        buContactUsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });


        return view;
    }

    /*@OnClick({R.id.img_bu_conact_us_back, R.id.image_contact_us_facebook, R.id.image_contact_us_instgram, R.id.image_contact_us_twitter, R.id.image_contact_us_youtube, R.id.bu_contact_us_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bu_conact_us_back:
                onBack();
                break;
            case R.id.image_contact_us_facebook:
                facebookURL =LoadData(getActivity(),"facebook_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL)));
                }
                break;
            case R.id.image_contact_us_instgram:
                instgramURL =LoadData(getActivity(),"instagram_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instgramURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(instgramURL)));
                }
                break;
            case R.id.image_contact_us_twitter:
                twitterURL =LoadData(getActivity(),"twitter_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL)));
                }
                break;
            case R.id.image_contact_us_youtube:
                youtubeURL =LoadData(getActivity(),"youtube_uri");
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL));
                    startActivity(intent);
                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeURL)));
                }
                break;
            case R.id.bu_contact_us_send:
                validate();
                break;
        }
    }*/

    private void validate(){
        if (etContactUsTitleOfMessage.getText().length()==0||etContactUsBodyOfMessage.getText().length()==0) {
            Toast.makeText(getActivity(),"please enter a message title or body",Toast.LENGTH_SHORT).show();
        }
        else{
            sendMessagesToAppManagement();
            Toast.makeText(getActivity(),"your message was sent",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessagesToAppManagement(){
        getClient().setContactUsMessage(API_TOKEN,etContactUsTitleOfMessage.getText().toString(),
                                        etContactUsBodyOfMessage.getText().toString());

        etContactUsTitleOfMessage.setText("");
        etContactUsBodyOfMessage.setText("");
    }

    @Override
    public void onBack() {
        replaceFragment(getFragmentManager(),R.id.frame_of_home_fragment_container,new MoreSettingsFragment());
    }
}
