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
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class HomeActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String PREFERENCES = "com.hasbrouckproductions.rhasbrouck.nhltracker.preferences";
    private static final String SAVEDSTRING = "com.hasbrouckproductions.rhasbrouck.nhltracker.saved_teams";

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


        //Restore Preferences
        SharedPreferences savedPreferences = getSharedPreferences(PREFERENCES, 0);
        String savedString = savedPreferences.getString(SAVEDSTRING, "");

        //If there is a saved string, update active team list
        if(!savedString.equals("")){
            StringTokenizer token = new StringTokenizer(savedString);
            while(token.hasMoreTokens()){
                int pos = teams.getPositionByCode(token.nextToken());
                teams.getTeam(pos).setSelected(true);
            }
        }


        //Set List View
        mTeamList = (ListView)findViewById(R.id.listView);
        adapter = new TeamArrayAdapter(this, R.layout.list_view_adapter, teams.getActiveTeams());
        mTeamList.setAdapter(adapter);

        //Add Listener for longTap on ListView
        mTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view, int position, long id) {
               //Going to go to new activity that shows more details about the team
                Team tempTeam = (Team)a.getItemAtPosition(position);
                //Toast.makeText(HomeActivity.this, tempTeam.getTeamName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(HomeActivity.this, TeamDetailActivity.class);
                intent.putExtra("TEAM_POSITION", teams.getPositionByCode(tempTeam.getTeamCode()));
                startActivityForResult(intent, 2);

            }
        });

        setAlarm();
    }

    //onPause save active teams to string
    @Override
    protected void onPause() {
        super.onPause();
        String saveString;
        saveString = setPreferencesString();
        SharedPreferences savedPreferences = this.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = savedPreferences.edit();
        editor.putString(SAVEDSTRING, saveString);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        teams.refreshData();
    }

    //get new team data from AddTeamActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Log.d("ACTIVITY HOME", "ON ACTIVITY RESULT POSITIVE");

                adapter = new TeamArrayAdapter(this, R.layout.list_view_adapter, teams.getActiveTeams());
                mTeamList.setAdapter(adapter);
                teams.setContext(this);
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Log.d("ACTIVITY HOME", "ON ACTIVITY RESULT POSITIVE FROM TEAM DETAIL");
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

    //gets active teams, puts them in string to save
    public String setPreferencesString(){
        ArrayList<Team> tempTeams = teams.getActiveTeams();
        String preferenceString = "";

        for(int i = 0; i < tempTeams.size(); i++){
            preferenceString += tempTeams.get(i).getTeamCode() + " ";
        }

        return preferenceString;
    }
}
