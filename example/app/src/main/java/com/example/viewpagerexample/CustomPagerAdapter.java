package com.example.viewpagerexample;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CustomPagerAdapter extends FragmentPagerAdapter {


    public CustomPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RedFragment.newInstance(0, "Red");
            case 1:
                return GreenFragment.newInstance(1, "Green");
            case 2:
                return BlueFragment.newInstance(2, "Blue");
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        position++;
        return "View " + position;
    }

}
