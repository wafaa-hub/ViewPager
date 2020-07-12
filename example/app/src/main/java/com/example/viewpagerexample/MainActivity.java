package com.example.viewpagerexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomPagerAdapter adapterViewPager;
    ViewPager vpPager;
    Button addView;
    Button deleteView;
    TabLayout tabLayout;
    public static final String FRAGMENT_TAG_ARG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        vpPager = findViewById (R.id.viewPager);
        addView = findViewById (R.id.add_view);
        deleteView = findViewById (R.id.delete_view);
        tabLayout = findViewById (R.id.tabLayout);
        adapterViewPager = new CustomPagerAdapter (getSupportFragmentManager ( ));
        vpPager.setAdapter (adapterViewPager);
        tabLayout.setupWithViewPager (vpPager);

        addView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                adapterViewPager.addFragment (PageFragment.newInstance ( ), "Page " + (adapterViewPager.getCount ( ) + 1), adapterViewPager.getCount ( ));

            }
        });

        deleteView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (adapterViewPager.getCount ( ) > 0 && tabLayout.getSelectedTabPosition() > -1) {
                    adapterViewPager.removeFragment ( tabLayout.getSelectedTabPosition ());

                }
            }
        });

        tabLayout.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener ( ) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (adapterViewPager.getCount ( ) > 0 && tabLayout.getSelectedTabPosition() > -1)
                    adapterViewPager.removeFragment (tab.getPosition ());

            }
        });
    }

    public class CustomPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments = new ArrayList<Fragment> ( );
        private ArrayList<String> fragmentsTitle = new ArrayList<String> ( );

        public CustomPagerAdapter(@NonNull FragmentManager fm) {
            super (fm);
        }

        @Override
        public int getCount() {
            return fragments.size ( );
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get (position);
        }

        public void addFragment(Fragment fragment, String title, int position) {
            fragments.add (fragment);
            fragmentsTitle.add (position, title);
            adapterViewPager.notifyDataSetChanged ( );
        }


        public void removeFragment( int position) {
            fragments.remove (position);
            fragmentsTitle.remove (position);
            adapterViewPager.notifyDataSetChanged ( );
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = ContextCompat.getDrawable (MainActivity.this, R.drawable.close);
            image.setBounds (0, 15, 60, 50);
            SpannableString sb = new SpannableString (" " + fragmentsTitle.get (position));
            ImageSpan imageSpan = new ImageSpan (image, ImageSpan.ALIGN_BASELINE);
            sb.setSpan (imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}
