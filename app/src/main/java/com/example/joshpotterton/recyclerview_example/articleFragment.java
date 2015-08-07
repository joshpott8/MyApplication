package com.example.joshpotterton.recyclerview_example;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class articleFragment extends Fragment {
    private View view;
    private int mPageNumber;
    private TextView tv;
    private TextView title;
    private ImageView image;

    public static articleFragment create(int pageNumber){
        articleFragment fragment = new articleFragment();
        Bundle args = new Bundle();
        args.putInt("position", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.article_item, container, false);
        mPageNumber = getArguments().getInt("position");
        tv = (TextView) view.findViewById(R.id.article);
        title = (TextView) view.findViewById(R.id.articleTitle);
        //image = (ImageView) view.findViewById(R.id.articleImage);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        title.setText(details.ListItems[mPageNumber]);
        tv.setText(details.Info[mPageNumber]);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if(sharedPreferences.getBoolean("pref_text_bold", false)){
            tv.setTypeface(null, Typeface.BOLD);
        }
        else{
            tv.setTypeface(null, Typeface.NORMAL);
        }

    }
}
