package com.example.joshpotterton.recyclerview_example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class menuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private static FragmentManager fragMan;
    private static AppCompatActivity activity;

    public static menuFragment create(FragmentManager fm, AppCompatActivity act){
        menuFragment frag = new menuFragment();
        fragMan = fm;
        activity = act;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.recycler, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(fragMan, activity);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

}
