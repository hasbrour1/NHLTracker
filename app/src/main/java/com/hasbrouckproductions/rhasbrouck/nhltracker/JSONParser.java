package com.hasbrouckproductions.rhasbrouck.nhltracker;

/**
 * Created by hasbrouckr on 4/25/2016.
 * PSONParser is an AsyncTask that will fetch the
 * JSON data with a supplied url, then parse the
 * data it gets.  It will then launch the alert for
 * any team activity for the day.
 *
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;




import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class JSONParser extends AsyncTask<String, Void, JSONObject> {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    static String result = "";
    static URL url;
    static HttpURLConnection urlConnection;
    static Context context;

    // constructor
    public JSONParser(Context c) {
        context = c;
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


            //Parse JSON Data
            try{
                jObj = new JSONObject(json);
                result = jObj.getString("timestamp");
            }catch(JSONException e){
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }


        }catch(IOException e){

        }finally {
            urlConnection.disconnect();
        }

        Log.d("JSON PARSER", "Finished doInBackground");
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.d("JSON PARSER", "ENTERED POST EXECUTE");
        Calendar c = Calendar.getInstance();
        SetAlarm();
        Log.d("JSON PARSER", "FINISHED");
    }


    public void SetAlarm()
    {

        //This will be ran once a day.
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive( Context context, Intent _ )
            {
                checkTeamUpdate();
                context.unregisterReceiver( this ); // this == BroadcastReceiver, not Activity
            }
        };

        context.registerReceiver( receiver, new IntentFilter("com.hasbrouckproductions.rhasbrouck.nhltracker.somemessage") );

        PendingIntent pintent = PendingIntent.getBroadcast( context, 0, new Intent("com.hasbrouckproductions.rhasbrouck.nhltracker.somemessage"), 0 );
        AlarmManager manager = (AlarmManager)(context.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        //TODO: change to once a day after testing is done
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000*5, pintent );
    }

    //TODO:Check to see if there is an event today with the json data.
    //TODO:If there is then set up a notification for it
    public void checkTeamUpdate(){
        String team1 = "";
        String team2 = "";

        Calendar date = Calendar.getInstance(); //todays date

        //TODO: check if something is happening same day as date
        if(true){
            //Issue notification for now, change it to check every day eventually
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.nhl_icon_black)
                            .setContentTitle("NHL Tracker")
                            .setContentText(team1 + " vs. " + team2 + " at " + date.getTime());

            int mNotificationId = 001;

            NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotifyMgr.notify(mNotificationId, mBuilder.build());

            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}