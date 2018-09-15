package com.idleciv.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaapo on 7-1-2018.
 */
@Deprecated
public class AdapterPageFragment extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTabNameList;

    public AdapterPageFragment(FragmentManager fm) {
        super(fm);
        this.mFragmentList = new ArrayList<>();
        this.mTabNameList =new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNameList.get(position);
    }

    public void setData(List<Fragment> fragments, List<String> tabNames) {
        this.mFragmentList = fragments;
        this.mTabNameList = tabNames;
        notifyDataSetChanged();
    }
}