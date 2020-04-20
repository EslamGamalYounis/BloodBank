package com.example.mybloodyapp.view.fragment.HomeCycleFragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.adapter.ViewPagerHomeAdapter;
import com.example.mybloodyapp.view.fragment.BaseFragment;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeContainerFragment extends BaseFragment {
    ArticleHomeNavFragment articleHomeNavFragment =new ArticleHomeNavFragment();
    DonationHomeNavFragment donationHomeNavFragment =new DonationHomeNavFragment();
    FragmentManager fragmentManager ;
    ViewPagerHomeAdapter viewPagerHomeAdapter;
    ViewPager viewPagerHome;
    TabLayout tabLayout;

    public HomeContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_container, container, false);
        setUpActivity();

        try {
            fragmentManager = getChildFragmentManager();

            viewPagerHome = view.findViewById(R.id.view_pager_home_fragment_container);
            tabLayout = view.findViewById(R.id.tabMode_fragment_home_container);

            viewPagerHomeAdapter = new ViewPagerHomeAdapter(fragmentManager,123);
            viewPagerHomeAdapter.addPager(articleHomeNavFragment, getActivity().getString(R.string.articles));
            viewPagerHomeAdapter.addPager(donationHomeNavFragment, getActivity().getString(R.string.donation_request));

            viewPagerHome.setAdapter(viewPagerHomeAdapter);

            tabLayout.setupWithViewPager(viewPagerHome);
        }
        catch (Exception e){
            Log.i("Error", "onCreateView: "+e.getMessage());
        }

        return view;
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

}
