package com.example.joshpotterton.recyclerview_example;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class viewPagerFragment extends Fragment {

    private static FragmentManager fragMan;
    private static AppCompatActivity activity;
    private View view;
    private static int position;

    public static viewPagerFragment create(FragmentManager fm, int pos, AppCompatActivity act){
        viewPagerFragment frag = new viewPagerFragment();
        fragMan = fm;
        position = pos;
        activity = act;
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.activity_screen_slide, container, false);



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new viewPagerFragment.ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        final ActionBar actionBar = activity.getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);
                actionBar.setSelectedNavigationItem(position);
            }
        });
        getActivity().setTitle("Article");


        ActionBar.TabListener tabListener = new ActionBar.TabListener(){

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                mPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        for(int i = 0; i <= 9; i++){
            actionBar.addTab(actionBar.newTab()
                    .setText("Item " + (i + 1))
                    .setTabListener(tabListener));
        }
        actionBar.setSelectedNavigationItem(position);
    }

    public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v("App Debug", Integer.toString(position));
            return articleFragment.create(position);
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position+1);
        }
    }
}
