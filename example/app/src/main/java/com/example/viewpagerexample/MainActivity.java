package com.example.viewpagerexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String FRAGMENT_TAG_ARG = "tag";

    CustomPagerAdapter adapterViewPager;
    ViewPager vpPager;
    Button addView;
    Button deleteView;
    RelativeLayout view;
    PagerTabStrip pagerTabStrip;
    Drawable removePage;
    ClickableSpan clickableSpan;
    ImageSpan span;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        vpPager = findViewById (R.id.viewpager1);
        addView = findViewById (R.id.add_view);
        deleteView = findViewById (R.id.delete_view);
        pagerTabStrip = findViewById (R.id.pager_header);
        adapterViewPager = new CustomPagerAdapter (getSupportFragmentManager ( ));
        adapterViewPager.addPage (PageFragment.newInstance ("Page " + count));
        adapterViewPager.notifyDataSetChanged ( );
        vpPager.setAdapter (adapterViewPager);

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
                if (adapterViewPager.getCount ( ) == 0)
                    count = 1;
                else {
                    count++;
                }
                adapterViewPager.addPage (PageFragment.newInstance ("Page " + count));
            }
        });

        deleteView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                if (adapterViewPager.getCount ( ) > 0)
                    adapterViewPager.removePage (vpPager.getCurrentItem ( ));
            }
        });
        clickableSpan = new ClickableSpan ( ) {
            @Override
            public void onClick(@NonNull View widget) {
                if (adapterViewPager.getCount ( ) > 0)
                    adapterViewPager.removePage (vpPager.getCurrentItem ( ));
            }
        };
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private List<Fragment> pages = new ArrayList<> ( );
        private Map<Fragment, Integer> fragmentsPosition = new HashMap<> ( );

        private Fragment currentPrimaryItem;
        private FragmentManager fragmentManager;
        private FragmentTransaction currentTransaction;


        public CustomPagerAdapter(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (currentTransaction == null) {
                currentTransaction = fragmentManager.beginTransaction ( );
            }

            Fragment pageFragment = pages.get (position);
            String tag = pageFragment.getArguments ( ).getString (MainActivity.FRAGMENT_TAG_ARG);
            Fragment fragment = fragmentManager.findFragmentByTag (tag);

            if (fragment != null) {
                if (fragment.getId ( ) == container.getId ( )) {
                    currentTransaction.attach (fragment);
                } else {
                    fragmentManager.beginTransaction ( ).remove (fragment).commit ( );
                    fragmentManager.executePendingTransactions ( );
                    currentTransaction.add (container.getId ( ), fragment, tag);
                }
            } else {
                fragment = pageFragment;
                currentTransaction.add (container.getId ( ), fragment, tag);
            }

            if (fragment != currentPrimaryItem) {
                fragment.setMenuVisibility (false);
                fragment.setUserVisibleHint (false);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return pages.size ( );
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (currentTransaction == null) {
                currentTransaction = fragmentManager.beginTransaction ( );
            }

            currentTransaction.detach ((Fragment) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Fragment fragment = (Fragment) object;

            if (fragment != currentPrimaryItem) {
                if (currentPrimaryItem != null) {
                    currentPrimaryItem.setMenuVisibility (false);
                    currentPrimaryItem.setUserVisibleHint (false);
                }

                if (fragment != null) {
                    fragment.setMenuVisibility (true);
                    fragment.setUserVisibleHint (true);
                }

                currentPrimaryItem = fragment;
            }
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            if (currentTransaction != null) {
                currentTransaction.commitAllowingStateLoss ( );
                currentTransaction = null;
                fragmentManager.executePendingTransactions ( );
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return ((Fragment) object).getView ( ) == view;
        }

        @Override
        public int getItemPosition(Object o) {
            Integer result = fragmentsPosition.get (o);

            if (result == null) {
                return PagerAdapter.POSITION_UNCHANGED;
            }

            return result;
        }


        public void addPage(Fragment fragment) {
            fragmentsPosition.clear ( );
            pages.add (fragment);
            adapterViewPager.notifyDataSetChanged ( );
        }

        public void removePage(int position) {
            fragmentsPosition.clear ( );
            Fragment pageFragment = pages.get (position);
            String tag = pageFragment.getArguments ( ).getString (MainActivity.FRAGMENT_TAG_ARG);

            Fragment fragment = fragmentManager.findFragmentByTag (tag);

            if (fragment != null) {
                fragmentsPosition.put (fragment, PagerAdapter.POSITION_NONE);
            }

            for (int i = position + 1; i < pages.size ( ); i++) {
                pageFragment = pages.get (i);
                tag = pageFragment.getArguments ( ).getString (MainActivity.FRAGMENT_TAG_ARG);
                fragment = fragmentManager.findFragmentByTag (tag);

                if (fragment != null) {
                    fragmentsPosition.put (fragment, i - 1);
                }
            }

            pages.remove (position);
            adapterViewPager.notifyDataSetChanged ( );

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            SpannableStringBuilder sb = new SpannableStringBuilder (" Page #" + (position + 1));
            removePage = getApplicationContext ( ).getDrawable (R.drawable.remove_page);
            removePage.setBounds (0, 15, 60, 50);
            span = new ImageSpan (removePage, ImageSpan.ALIGN_BASELINE);
            sb.setSpan (span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            return sb;
        }
    }
}
