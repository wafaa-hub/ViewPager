package com.example.viewpagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    CustomPagerAdapter adapterViewPager;
    ViewPager vpPager;
    Button addView;
    Button deleteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpPager = findViewById(R.id.viewpager1);
        addView = findViewById (R.id.add_view);
        deleteView = findViewById (R.id.delete_view);
        adapterViewPager = new CustomPagerAdapter ();
        vpPager.setAdapter(adapterViewPager);
        LayoutInflater inflater = getLayoutInflater();

        RelativeLayout v0 = (RelativeLayout) inflater.inflate (R.layout.view_red, null);
        adapterViewPager.addView (v0, 0);
        RelativeLayout v1 = (RelativeLayout) inflater.inflate (R.layout.view_green, null);
        adapterViewPager.addView (v1, 1);
        RelativeLayout v2= (RelativeLayout) inflater.inflate (R.layout.view_blue, null);
        adapterViewPager.addView (v2, 2);
        adapterViewPager.notifyDataSetChanged();

        addView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                int pageIndex = adapterViewPager.addView (v);
                 vpPager.setCurrentItem (pageIndex, true);
            }
        });

        deleteView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
             if(vpPager.getCurrentItem () > 0) {
                 int pageIndex = adapterViewPager.removeView (vpPager, v);
                 if (pageIndex == adapterViewPager.getCount ( ))
                     pageIndex--;
                 vpPager.setCurrentItem (pageIndex);
             }
            }
        });
    }

}
