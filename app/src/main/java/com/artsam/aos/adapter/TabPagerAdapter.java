package com.artsam.aos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.artsam.aos.fragment.Data;
import com.artsam.aos.fragment.Users;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public TabPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragment for Data Tab
                return new Data();
            case 1:
                //Fragment for Users Tab
                return new Users();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}