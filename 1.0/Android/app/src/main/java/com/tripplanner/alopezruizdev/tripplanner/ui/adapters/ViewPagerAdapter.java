package com.tripplanner.alopezruizdev.tripplanner.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tripplanner.alopezruizdev.tripplanner.ui.fragments.ListFragment;
import com.tripplanner.alopezruizdev.tripplanner.ui.fragments.MapFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    private static int TAB_COUNT = 2;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ListFragment.newInstance();
            case 1:
                return MapFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
