package com.example.mybloodyapp.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;


public class ViewPagerHomeAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentsTitle = new ArrayList<>();

    public ViewPagerHomeAdapter(@NonNull FragmentManager fm,int behaviour) {
        super(fm,behaviour);
        fragments = new ArrayList<>();
        fragmentsTitle = new ArrayList<>();
    }

    public void addPager(Fragment fragments, String fragmentTitle) {
        this.fragments.add(fragments);
        this.fragmentsTitle.add(fragmentTitle);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle.get(position);
    }
}
