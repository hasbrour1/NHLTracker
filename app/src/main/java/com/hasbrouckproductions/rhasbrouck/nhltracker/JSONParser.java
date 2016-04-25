package com.hasbrouckproductions.rhasbrouck.nhltracker;

/**
 * Created by hasbrouckr on 4/25/2016.
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class JSONParser extends AsyncTask<String, Void, JSONObject> {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    static URL url;
    static HttpURLConnection urlConnection;

    // constructor
    public JSONParser() {

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            url = new URL(params[0]);
        }catch (MalformedURLException e){
            Log.e("JSON ERROR", e.toString());
        }



        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            StringBuilder sb=new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;

            while((read=br.readLine()) != null) {
                sb.append(read);
            }

            br.close();
            json = sb.toString();


            try{
                jObj = new JSONObject("{" + json +"}");
            }catch(JSONException e){
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }


        }catch(IOException e){

        }finally {
            urlConnection.disconnect();
        }

        //Use JSON Data

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}