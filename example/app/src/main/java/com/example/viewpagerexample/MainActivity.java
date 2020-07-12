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

    CustomViewPagerAdapter adapterViewPager;
    ViewPager viewPager;
    Button addFragmentBtn;
    Button deleteFragmentBtn;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        viewPager = findViewById (R.id.viewPager);
        addFragmentBtn = findViewById (R.id.add_view);
        deleteFragmentBtn = findViewById (R.id.delete_view);
        tabLayout = findViewById (R.id.tabLayout);
        adapterViewPager = new CustomViewPagerAdapter (getSupportFragmentManager ( ));
        viewPager.setAdapter (adapterViewPager);
        tabLayout.setupWithViewPager (viewPager);

        addFragmentBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                adapterViewPager.addFragment (PageFragment.newInstance ( ), "Page " + (adapterViewPager.getCount ( ) + 1), adapterViewPager.getCount ( ));

            }
        });

        deleteFragmentBtn.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (adapterViewPager.getCount ( ) > 0 && tabLayout.getSelectedTabPosition ( ) > -1) {
                    adapterViewPager.removeFragment (tabLayout.getSelectedTabPosition ( ));

                }
            }
        });

        tabLayout.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener ( ) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (adapterViewPager.getCount ( ) > 0 && tabLayout.getSelectedTabPosition ( ) > -1)
                    adapterViewPager.removeFragment (tab.getPosition ( ));
            }
        });
    }

    public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments = new ArrayList<Fragment> ( );
        private ArrayList<String> fragmentsTitle = new ArrayList<String> ( );

        CustomViewPagerAdapter(@NonNull FragmentManager fm) {
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

        void addFragment(Fragment fragment, String title, int position) {
            fragments.add (fragment);
            fragmentsTitle.add (position, title);
            adapterViewPager.notifyDataSetChanged ( );
        }


        void removeFragment(int position) {
            fragments.remove (position);
            fragmentsTitle.remove (position);
            adapterViewPager.notifyDataSetChanged ( );
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable image = ContextCompat.getDrawable (MainActivity.this, R.drawable.close);
            assert image != null;
            image.setBounds (0, 15, 60, 50);
            SpannableString sb = new SpannableString (" " + fragmentsTitle.get (position));
            ImageSpan imageSpan = new ImageSpan (image, ImageSpan.ALIGN_BASELINE);
            sb.setSpan (imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
        }
    }
}
