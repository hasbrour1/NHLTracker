package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*
    The HomeActivity will be the main activity of NHLTracker.  It will list the added teams, a button
    to add or remove teams, and a refresh button to refresh data for now.  I will remove the refresh
    button eventually and auto refresh.

 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button mAddTeam;
    private Button mRemoveTeam;
    private Button mRefresh;
    private ListView mTeamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Set List View
        mTeamList = (ListView)findViewById(R.id.listView);

        //Set fake teams list
        List<Team> teams = new ArrayList<Team>();

        teams.add(new Team("New York Rangers", "NYR"));
        teams.add(new Team("Penguins", "PBP"));


        //Set Adapter for mTeamList
        TeamArrayAdapter adapter = new TeamArrayAdapter(this, R.layout.list_view_adapter, teams);
        mTeamList.setAdapter(adapter);

        //Add listener for add Team Button
        mAddTeam = (Button) findViewById(R.id.addTeamButton);
        mAddTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                //Open the AddTeam Activity
                Intent addTeamIntent = new Intent(V.getContext(), AddTeamActivity.class);
                startActivity(addTeamIntent);

                //TODO: get intent from AddTeam with 2 strings with
                //Team Name and 3 Letter Code

                //TODO: Add Team to mTeamList


            }
        });

        //Add listener for Remove Team Button
        mRemoveTeam = (Button)findViewById(R.id.removeTeamButton);
        mRemoveTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: Show prompt to select which team to remove

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

    //refreshData will get the data from
    //http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/NYR/2016/04/iphone/clubschedule.json
    //replace NYR with selected team code, and the date with current year and month.
    //use https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
    //and http://www.jsoneditoronline.org/?url=http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/NYR/2016/04/iphone/clubschedule.json
    //for help
    public void refreshData(){

    }
}
