package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*
    The HomeActivity will be the main activity of NHLTracker.  It will list the added teams, a button
    to add or remove teams, and a refresh button to refresh data for now.  I will remove the refresh
    button eventually and auto refresh.

 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.hasbrouckproductions.database.TeamDbSchema.rhasbrouck.nhltracker.TeamBaseHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";

    private ListView mTeamList;
    private AlarmManager mManager;

    private Menu mMenu;

    private Teams teams;

    TeamArrayAdapter adapter;

    private SQLiteDatabase mDatabase;

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

        //Create Database
        mDatabase = new TeamBaseHelper(this).getWritableDatabase();


        teams = Teams.getInstance();



        //Set List View
        mTeamList = (ListView)findViewById(R.id.listView);

        //Check if saved instance state
        if(savedInstanceState == null){

        }else{

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
                teams.setContext(this);
                teams.refreshData();
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
        teams.setContext(HomeActivity.this);
        //This will be ran once a day to check if there are any game
        //events
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        mManager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 50 sec (1000*50) from now (SystemClock.elapsedRealtime())
        //TODO: change to once a day after testing is done
        mManager.setRepeating( AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (1000*60), pendingIntent );
    }



}
