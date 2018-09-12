package com.idleciv.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jaapo on 7-1-2018.
 */

public class AdapterPage extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> mTabNameList;

    public AdapterPage(FragmentManager fm, List<Fragment> fragments, List<String> tabNames) {
        super(fm);
        this.mFragmentList = fragments;
        this.mTabNameList =tabNames;
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
}