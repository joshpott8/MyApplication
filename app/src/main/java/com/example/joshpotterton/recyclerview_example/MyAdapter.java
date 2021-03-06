package com.example.joshpotterton.recyclerview_example;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static Context context;
    private static FragmentManager fragMan;
    private static Activity activity;

    public ViewPager mPager;
    public PagerAdapter mPagerAdapter;

    public MyAdapter(FragmentManager fm, Activity act){
        fragMan = fm;
        activity = act;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(view, mPager, mPagerAdapter, activity);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.title.setText(details.ListItems[position]);
        //holder.desc.setText(details.Info[position]);

        String str = "";
        for(int i = 0; i <= 40; i++){
            str = str + details.Info[position].charAt(i);
        }

        str = str + "...";
        holder.desc.setText(str);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{
        public TextView title;
        public TextView desc;
        private ViewPager mPager;
        private PagerAdapter mPagerAdapter;
        private Activity activity;

        public ViewHolder(View itemView, ViewPager v, PagerAdapter p, Activity act) {
            super(itemView);
            activity = act;
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "You clicked item " + (getLayoutPosition()+1), Toast.LENGTH_SHORT).show();

            viewPagerFragment frag = viewPagerFragment.create(fragMan, getLayoutPosition());
            Bundle args = new Bundle();
            //args.putInt("position", getLayoutPosition());
            //frag.setArguments(args);
            FragmentTransaction transaction = fragMan.beginTransaction();
            transaction.replace(R.id.frame_layout, frag);
            transaction.addToBackStack(null);

            transaction.commit();

        }
    }

}
