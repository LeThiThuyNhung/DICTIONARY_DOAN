package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new Fragment_AnhViet();
                break;

            case 1:
                fragment = new Fragment_NghiaCuaBan();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
