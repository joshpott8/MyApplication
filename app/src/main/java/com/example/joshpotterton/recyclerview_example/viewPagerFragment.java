package com.example.joshpotterton.recyclerview_example;


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
    private View view;
    private static int position;

    public static viewPagerFragment create(FragmentManager fm, int pos){
        viewPagerFragment frag = new viewPagerFragment();
        fragMan = fm;
        position = pos;
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
        ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter mPagerAdapter = new viewPagerFragment.ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(position);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);
                getActivity().invalidateOptionsMenu();
            }
        });
        getActivity().setTitle("Article");


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
    }
}
