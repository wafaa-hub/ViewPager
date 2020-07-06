package com.example.viewpagerexample;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private ArrayList<View> views = new ArrayList<View>();

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        position++;
        return "View " + position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView (views.get (position));
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition (Object object)
    {
        int index = views.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = views.get (position);
        container.addView (v);
        return v;
    }

    public int addView (View v, int position)
    {
        views.add (position, v);
        return position;
    }

    public int addView (View v)
    {
        return addView (v, views.size());
    }

    public int removeView (ViewPager pager, View v)
    {
        return removeView (pager, views.indexOf (v));
    }

    public int removeView(ViewPager pager, int position) {
        pager.setAdapter (null);
        views.remove (position);
        pager.setAdapter (this);

        return position;
    }

    public View getView (int position)
    {
        return views.get (position);
    }

}
