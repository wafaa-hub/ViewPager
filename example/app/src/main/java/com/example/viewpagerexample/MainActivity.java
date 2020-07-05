package com.example.viewpagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        ViewPager viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        viewPager1.setAdapter(new CustomPagerAdapter(this));

        ViewPager viewPager2 = (ViewPager) findViewById(R.id.viewpager2);
        viewPager2.setAdapter(new CustomPagerAdapter(this));


    }
}
