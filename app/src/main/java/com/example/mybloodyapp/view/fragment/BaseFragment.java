package com.example.mybloodyapp.view.fragment;



import androidx.fragment.app.Fragment;
import com.example.mybloodyapp.view.activity.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity =(BaseActivity)getActivity();
        baseActivity.baseFragment=this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }

}
