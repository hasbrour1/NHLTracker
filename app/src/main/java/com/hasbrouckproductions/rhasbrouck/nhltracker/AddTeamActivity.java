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
    public Teams teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        mListView = (ListView)findViewById(R.id.add_team_list_view);
        teams = Teams.getInstance();

        final AddTeamAdapter adapter = new AddTeamAdapter(this, R.layout.team_list_view_adapter, teams.getList());
        mListView.setAdapter(adapter);


        //add Enter Button Listener
        mEnterButton = (Button)findViewById(R.id.enterButton);
        mEnterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                        //Send intent back to HomeActivity
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
            }
        });
    }
}
