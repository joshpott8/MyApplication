package com.example.joshpotterton.recyclerview_example;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.ref.WeakReference;

public class articleFragment extends Fragment {
    private View view;
    private int mPageNumber;
    private TextView tv;
    private TextView title;
    private ImageView image;
    private SwipeRefreshLayout swipeRefresh;

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



        image = (ImageView) view.findViewById(R.id.imageView);
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //title.setText(details.ListItems[mPageNumber]);
        //tv.setText(details.Info[mPageNumber]);

        try {

            if(mPageNumber < 2){
                JSONObject reader = new JSONObject(details.json);
                JSONArray jsonArray = reader.optJSONArray("Article");
                JSONObject article = jsonArray.getJSONObject(mPageNumber);
                title.setText(article.getString("Title"));
                tv.setText(article.getString("Description"));
            }
            else if(mPageNumber < 3){

                xmlParsing p = new xmlParsing(title, tv);
                // p.doInBackground();
                p.onPostExecute(p.doInBackground());

            }
            else{
                title.setText(details.ListItems[mPageNumber]);
                tv.setText(details.Info[mPageNumber]);
            }
        }
        catch(Exception e){
            Log.v("App Debug", e.getMessage());
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                updateFrag();
                swipeRefresh.setRefreshing(false);
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        tv.setTextSize(Float.parseFloat(sharedPreferences.getString("pref_text_size", "16")));

        switch(sharedPreferences.getString("pref_text_style", "Sans")){
            case "Sans" :
                if(sharedPreferences.getBoolean("pref_text_bold", false)) {
                    tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                }
                else{
                    tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                }
                break;
            case "Serif" :
                if(sharedPreferences.getBoolean("pref_text_bold", false)) {
                    tv.setTypeface(Typeface.SERIF, Typeface.BOLD);
                }
                else{
                    tv.setTypeface(Typeface.SERIF, Typeface.NORMAL);
                }
                break;
            case "Monospace" :
                if(sharedPreferences.getBoolean("pref_text_bold", false)) {
                    tv.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                }
                else{
                    tv.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
                }
                break;
        }

        BitmapWorkerTask bm = new BitmapWorkerTask(image);
        bm.onPostExecute(bm.doInBackground(R.drawable.trollface));

    }

    public void updateFrag(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        tv.setTextSize(Float.parseFloat(sharedPreferences.getString("pref_text_size", "16")));

        switch(sharedPreferences.getString("pref_text_style", "Sans")){
            case "Sans" :
                if(sharedPreferences.getBoolean("pref_text_bold", false)) {
                    tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                }
                else{
                    tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                }
                break;
            case "Serif" :
                if(sharedPreferences.getBoolean("pref_text_bold", false)) {
                    tv.setTypeface(Typeface.SERIF, Typeface.BOLD);
                }
                else{
                    tv.setTypeface(Typeface.SERIF, Typeface.NORMAL);
                }
                break;
            case "Monospace" :
                if(sharedPreferences.getBoolean("pref_text_bold", false)) {
                    tv.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                }
                else{
                    tv.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
                }
                break;
        }

    }

    private class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>{

        private final WeakReference<ImageView> imageView;
        int data;

        public BitmapWorkerTask(ImageView imageView){
            this.imageView = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            Log.v("App Debug", "Working in Background");
            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
        }

        protected void onPostExecute(Bitmap bitmap){
            if(imageView != null && bitmap != null){
                final ImageView image = imageView.get();
                if(image != null){
                    Log.v("App Debug", "Image set");
                    image.setImageBitmap(bitmap);
                }
            }
        }


    }

    public class xmlParsing extends AsyncTask<Void, Void, String[]>{

        private XmlPullParser xpp;
        private TextView mTitle;
        private TextView mDesc;


        public xmlParsing(TextView title, TextView desc){
            mTitle = title;
            mDesc = desc;
        }

        @Override
        protected String[] doInBackground(Void... params) {
            String[] str = new String[2];
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                xpp = factory.newPullParser();
                xpp.setInput(new StringReader(details.xmlString));

                Log.v("App Debug", "Parsing XML");

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if ((eventType == XmlPullParser.START_TAG) && (!xpp.getName().equals("Articles"))) {
                            Log.v("App Debug", "xpp.getName() = " + xpp.getName());
                            xpp.next();
                            xpp.next();
                            str[0] = xpp.getText();
                            xpp.next();
                            xpp.next();
                            xpp.next();
                            str[1] = xpp.getText();
                            //Log.v("App Debug", Title + " - " + Desc);
                            break;

                    }
                    else{
                        if(eventType == XmlPullParser.TEXT) {
                            Log.v("App Debug", xpp.getText());
                        }
                    }
                    eventType = xpp.next();

                }
            }
            catch(Exception e){
                Log.v("App Debug", e.getMessage());
            }
            return str;
        }

        @Override
        protected void onPostExecute(String[] str) {
            super.onPostExecute(str);
            mTitle.setText(str[0]);
            mDesc.setText(str[1]);
        }
    }

    //Code from Google Android Developer webpage
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
