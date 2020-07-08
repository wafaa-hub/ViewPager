package com.example.viewpagerexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomPagerAdapter adapterViewPager;
    ViewPager vpPager;
    Button addView;
    Button deleteView;
    RelativeLayout view;
    PagerTabStrip pagerTabStrip;
    Drawable removePage ;
    ClickableSpan clickableSpan;
    ImageSpan span;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        vpPager = findViewById (R.id.viewpager1);
        addView = findViewById (R.id.add_view);
        deleteView = findViewById (R.id.delete_view);
        pagerTabStrip = findViewById (R.id.pager_header);
        adapterViewPager = new CustomPagerAdapter ( );
        vpPager.setAdapter (adapterViewPager);
        LayoutInflater inflater = getLayoutInflater ( );

        view = (RelativeLayout) inflater.inflate (R.layout.page, null);

        pagerTabStrip.setOnTouchListener (new View.OnTouchListener ( ) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clickableSpan.onClick (view);
                return false;
            }
        });


        addView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                int pageIndex = adapterViewPager.addView (view);
                vpPager.setCurrentItem (pageIndex, true);
            }
        });

        deleteView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (adapterViewPager.getCount ( ) > 0) {
                    int pageIndex = adapterViewPager.removeView (vpPager, view);
                    if (pageIndex == adapterViewPager.getCount ( ))
                        pageIndex--;
                    vpPager.setCurrentItem (pageIndex);
                }
            }
        });

        clickableSpan = new ClickableSpan ( ) {
            @Override

            public void onClick(@NonNull View widget) {
                if (adapterViewPager.getCount ( ) > 0) {
                    int pageIndex = adapterViewPager.removeView (vpPager, view);
                    if (pageIndex == adapterViewPager.getCount ( ))
                        pageIndex--;
                    vpPager.setCurrentItem (pageIndex);
                }
            }
        };
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private ArrayList<View> views = new ArrayList<View> ( );

        @Override
        public int getCount() {
            return views.size ( );
        }

        @Override
        public CharSequence getPageTitle(final int position) {

            SpannableStringBuilder sb = new SpannableStringBuilder (" Page #" + (position + 1));
            removePage = getApplicationContext ( ).getDrawable (R.drawable.remove_page);
            removePage.setBounds (0, 15, 60, 50);
            span = new ImageSpan (removePage, ImageSpan.ALIGN_BASELINE);
            sb.setSpan (span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return sb;
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
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View v = views.get (position);
            if (v.getParent ( ) != null) {
                ((ViewGroup) v.getParent ( )).removeView (v);
            }
            container.addView (v);
            return v;
        }

        public int addView(View v, int position) {
            views.add (position, v);
            adapterViewPager.notifyDataSetChanged ( );
            return position;
        }

        public int addView(View v) {
            return addView (v, views.size ( ));
        }

        public int removeView(ViewPager pager, View v) {
            return removeView (pager, views.indexOf (v));
        }

        public int removeView(ViewPager pager, int position) {
            pager.setAdapter (null);
            views.remove (position);
            pager.setAdapter (this);
            adapterViewPager.notifyDataSetChanged ( );
            return position;
        }
    }


}
