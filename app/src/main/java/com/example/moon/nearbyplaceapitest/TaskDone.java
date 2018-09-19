package com.example.moon.nearbyplaceapitest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TaskDone extends AsyncTask<String,String,String> {
    @Override
    protected String doInBackground(String... strings) {
        String FinalString = "****";
        try {
            Log.i("path",strings[0]);
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader  = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            FinalString = stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FinalString;
    }

}
