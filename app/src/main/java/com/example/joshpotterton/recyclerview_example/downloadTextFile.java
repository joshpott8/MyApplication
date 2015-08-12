package com.example.joshpotterton.recyclerview_example;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class downloadTextFile extends AsyncTask<String, Void, String> {

    private TextView textView;

    public downloadTextFile(TextView tv){
        textView = tv;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        try{
            URL fileURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());

            BufferedReader inputStream = new BufferedReader(isr);

            StringBuilder stringBuilder = new StringBuilder();
            String str;

            while((str = inputStream.readLine()) != null){
                stringBuilder.append(str + "\n");
            }


            String text = stringBuilder.toString();

            connection.disconnect();
            return text;
        }
        catch (Exception e){
            Log.v("App Debug", "Error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


}
