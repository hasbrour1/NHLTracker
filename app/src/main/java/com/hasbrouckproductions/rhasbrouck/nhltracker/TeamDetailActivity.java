package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamDetailActivity extends AppCompatActivity {

    public Teams mTeams;
    private int teamPos;

    private TextView mTeamName;
    private ListView mListView;
    private TeamDetailAdapter adapter;
    private ImageView teamIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);
        mTeams = Teams.getInstance();

        Bundle extras = getIntent().getExtras();

        //if no position is provided, just return to HomeActivity
        if(extras == null){
            //Send intent back to HomeActivity
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }else{
            teamPos = extras.getInt("TEAM_POSITION");
        }

        teamIcon = (ImageView)findViewById(R.id.team_detail_image_view);
        teamIcon.setImageResource(mTeams.getTeam(teamPos).getTeamIcon());

        mTeamName = (TextView)findViewById(R.id.team_detail_team_name);
        mTeamName.setText(mTeams.getTeam(teamPos).getTeamName());

        mListView = (ListView)findViewById(R.id.team_detail_list_view);
        adapter = new TeamDetailAdapter(this, R.layout.team_detail_list_view_adapter, mTeams.getTeam(teamPos).getGames(), mTeams.getTeam(teamPos));

        mListView.setAdapter(adapter);

    }
}
