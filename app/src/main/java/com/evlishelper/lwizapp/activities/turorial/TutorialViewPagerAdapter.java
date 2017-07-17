package com.evlishelper.lwizapp.activities.turorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TutorialViewPagerAdapter extends FragmentPagerAdapter {
    public TutorialViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new GreetingFragment();
                break;
            case 1:
                fragment = new TunerTutorialFragment();
                break;
            case 2:
                fragment = new TestTutorialFragment();
                break;
            case 3:
                fragment = new NotesTutorialFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}