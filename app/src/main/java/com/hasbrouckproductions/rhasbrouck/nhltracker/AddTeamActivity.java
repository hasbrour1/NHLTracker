package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*

    AddTeamActivity will take the team name and 3 letter code and send it with an intent to
    the HomeActivity

    TODO:  Change class to open a list view with ability to check list of teams instead of manually typing them


 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddTeamActivity extends AppCompatActivity {

    public Button mEnterButton;
    public ListView mListView;
    public Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        mListView = (ListView)findViewById(R.id.add_team_list_view);
        String[] sTeams = new String[]{"ANA", "Anaheim Ducks", "ARI", "Arizona Coyotes", "BOS", "Boston Bruins",
                "BUF", "Buffalo Sabres", "CAR", "Carolina Hurricanes", "CBJ", "Columbus Blue Jackets", "CGY",
                "Calgary Flames", "CHI", "Chicago Black Hawks", "COL", "Colorado Avalanche", "DAL", "Dallas Stars",
                "DED", "Detroit Red Wings", "EDM", "Edmonton Oilers", "FLA", "Florida Panthers", "LAK", "Los Angeles Knights",
                "MIN", "Minnesota Wild", "MTL", "Montreal Canadians", "NJD", "New Jersey Devils", "NSH", "Nashville Predators",
                "NYI", "New York Islanders", "NYR", "New York Rangers", "Ottawa Senators", "Philadelphia Flyers",
                "OTT", "Ottawa Senators", "PHI", "Philadelphia Flyers", "PIT", "Pittsburgh Penguins", "SJS", "San Jose Sharks",
                "STL", "St Louis Blues", "TBL", "Tampa Bay Lightning", "TOR", "Toronto Maple Leafs", "VAN", "Vancouver Canucks",
                "WPG", "Winnipeg Jets", "WSH", "Washington Capitals"

        };

        Teams teams = Teams.getInstance();

        for(int i = 0; i < sTeams.length; i ++){
            teams.addTeam(new Team(sTeams[i +1], sTeams[i]));
            i++;
        }

        final AddTeamAdapter adapter = new AddTeamAdapter(this, R.layout.team_list_view_adapter, teams.getList());
        mListView.setAdapter(adapter);

        //add Enter Button Listener
        mEnterButton = (Button)findViewById(R.id.enterButton);
        mEnterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String teamName, teamCode;

                //Check if both edit texts are filled and send in intent
                teamName ="";
                teamCode = "";

                //check team code is only 3
                if(teamCode.length() != 3){
                    Toast.makeText(v.getContext(), "Error: Team Code must be 3 letters", Toast.LENGTH_SHORT).show();
                }else{
                    //make sure team name is filled
                    if(teamName.equals("")){
                        Toast.makeText(v.getContext(), "Error: Please Enter Team Name", Toast.LENGTH_SHORT).show();
                    }else {
                        //Send intent back to HomeActivity
                        Intent intent = new Intent();
                        intent.putExtra("team_name", teamName);
                        intent.putExtra("team_code", teamCode);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            }
        });
    }
}
