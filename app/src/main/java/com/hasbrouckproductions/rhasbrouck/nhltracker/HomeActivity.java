package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*
    The HomeActivity will be the main activity of NHLTracker.  It will list the added teams, a button
    to add or remove teams, and a refresh button to refresh data for now.  I will remove the refresh
    button eventually and auto refresh.

 */
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";

    private Button mAddTeam;
    private Button mRefresh;
    private ListView mTeamList;

    private ArrayList<Team> teams;

    TeamArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set List View
        mTeamList = (ListView)findViewById(R.id.listView);


        //Check if saved instance state
        if(savedInstanceState == null){
            //Set fake teams list
            teams = new ArrayList<Team>();

            //remove default teams eventually
            teams.add(new Team("New York Rangers", "NYR"));
            teams.add(new Team("Penguins", "PBP"));
            Log.d("HOME_ACTIVITY", "savedstate = null");
        }else{
            Log.d("HOME_ACTIVITY", "savedstate != null");
            teams = savedInstanceState.getParcelableArrayList(KEY_INDEX);
        }

        //Set Adapter for mTeamList
        adapter = new TeamArrayAdapter(this, R.layout.list_view_adapter, teams);
        mTeamList.setAdapter(adapter);

        //Add listener for add Team Button
        mAddTeam = (Button) findViewById(R.id.addTeamButton);
        mAddTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                //Open the AddTeam Activity
                Intent addTeamIntent = new Intent(V.getContext(), AddTeamActivity.class);
                startActivityForResult(addTeamIntent, 1);

            }
        });

        //Add Listener for longTap on ListView
        mTeamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(HomeActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + teams.get(position).getTeamName());
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        teams.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
            }
        });

        //Add Listener for Refresh Button
        mRefresh = (Button)findViewById(R.id.refreshButton);
        mRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //run refresh Data
                refreshData();
            }
        });

    }

    //get new team data from AddTeamActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String teamCode = data.getStringExtra("team_code");
                String teamName = data.getStringExtra("team_name");

                Log.d("ACTIVITY HOME", "ON ACTIVITY RESULT");
                //Add Team to mTeamList
                if(teams != null) {
                    teams.add(new Team(teamName, teamCode));
                    Log.d("ACTIVITY HOME", "ADDED TEAM NAME AND CODE");
                }else{
                    Log.d("ACTIVITY HOME", "TEAMS = NULL");
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_INDEX, teams);
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
            teamCode = teams.get(i).getTeamCode();
            teamUrl = "http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/" + teamCode + "/" +
                    sYear +"/" + sMonth + "/iphone/clubschedule.json";

            // Getting JSON Object
            new JSONParser(getApplicationContext(), teamCode).execute(teamUrl);
        }
    }


}
