package com.example.joshpotterton.recyclerview_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class favArticlesFragment extends Fragment {

    private ListView listView;
    private static dbHelper database;

    public static favArticlesFragment create(dbHelper db){
        database = db;
        favArticlesFragment frag = new favArticlesFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite_articles, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<HashMap<String, String>> articles = database.getArticles();

        ArrayAdapter<String> arrayAdapter;

        if(articles.isEmpty()){
            ArrayList<String> array = new ArrayList<>();
            array.add("Empty");
            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
            listView.setAdapter(arrayAdapter);
        }
        else{
            ArrayList<String> array = new ArrayList<>();
            for(HashMap<String, String> article : articles){
                array.add(article.get("Title"));

            }
            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, array);
            listView.setAdapter(arrayAdapter);
        }

    }
}
