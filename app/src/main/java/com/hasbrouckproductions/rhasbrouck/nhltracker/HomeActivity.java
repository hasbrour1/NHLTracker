package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*
    The HomeActivity will be the main activity of NHLTracker.  It will list the added teams, a button
    to add or remove teams, and a refresh button to refresh data for now.  I will remove the refresh
    button eventually and auto refresh.

 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";

    private ListView mTeamList;
    private AlarmManager mManager;

    private Menu mMenu;

    private Teams teams;

    TeamArrayAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.main_menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // as before
        switch(item.getItemId()) {
            case R.id.add_remove_option:
                //Open the AddTeam Activity
                Intent addTeamIntent = new Intent(this, AddTeamActivity.class);
                startActivityForResult(addTeamIntent, 1);

            // rest as before
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        teams = Teams.getInstance();

        //Set List View
        mTeamList = (ListView)findViewById(R.id.listView);

        //Check if saved instance state
        if(savedInstanceState == null){
            Log.d("HOME_ACTIVITY", "savedstate = null");
        }else{
            Log.d("HOME_ACTIVITY", "savedstate != null");
        }

        adapter = new TeamArrayAdapter(this, R.layout.list_view_adapter, teams.getActiveTeams());


        mTeamList.setAdapter(adapter);

        //Add Listener for longTap on ListView
        mTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view, int position, long id) {
               //Going to go to new activity that shows more details about the team
            }
        });

        setAlarm();
    }

    //get new team data from AddTeamActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Log.d("ACTIVITY HOME", "ON ACTIVITY RESULT POSITIVE");
                //Add Team to mTeamList
                ArrayList<Team> temTeams;
                temTeams = teams.getActiveTeams();

                adapter = new TeamArrayAdapter(this, R.layout.list_view_adapter, teams.getActiveTeams());
                mTeamList.setAdapter(adapter);
                refreshData();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_INDEX, teams.getList());
    }

    //seting an alarm for once a day
    //if app is destroyed then the alarm will be turned off
    public void setAlarm()
    {

        //This will be ran once a day to check if there are any game
        //events
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent _ )
            {
                Log.d("BROADCAST RECIEVER", "Refreshing data");
                refreshData();
                context.unregisterReceiver( this ); // this == BroadcastReceiver, not Activity
            }
        };

        this.registerReceiver( receiver, new IntentFilter("com.hasbrouckproductions.rhasbrouck.nhltracker.somemessage") );

        PendingIntent pintent = PendingIntent.getBroadcast( this, 0, new Intent("com.hasbrouckproductions.rhasbrouck.nhltracker.somemessage"), 0 );
        mManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 50 sec (1000*50) from now (SystemClock.elapsedRealtime())
        //TODO: change to once a day after testing is done
        mManager.setRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000*60), pintent );
    }


    //refreshData will get the data from
    //http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/NYR/2016/04/iphone/clubschedule.json
    //replace NYR with selected team code, and the date with current year and month.
    //use https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
    //and http://www.jsoneditoronline.org/?url=http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/NYR/2016/04/iphone/clubschedule.json
    //for help
    public void refreshData(){

        String teamCode;
        String teamUrl;
        String sMonth;
        String sYear;
        Calendar date = Calendar.getInstance();
        SimpleDateFormat month = new SimpleDateFormat("LL");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        sMonth = month.format(date.getTime());
        sYear = year.format(date.getTime());

        /*  Test to make sure they contain correct year and month
        String str = "YEAR: " + sYear + "MONTH: " + sMonth;

        Toast.makeText(HomeActivity.this, str, Toast.LENGTH_LONG).show();
        */

        //get team names and date to get json data
        for(int i = 0; i < teams.size(); i++){
            if(teams.getTeam(i).isSelected()){
                teamCode = teams.getTeam(i).getTeamCode();
                teamUrl = "http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/" + teamCode + "/" +
                        sYear +"/" + sMonth + "/iphone/clubschedule.json";

                // Getting JSON Object
                new JSONParser(getApplicationContext(), teamCode).execute(teamUrl);
            }
        }
    }
}
