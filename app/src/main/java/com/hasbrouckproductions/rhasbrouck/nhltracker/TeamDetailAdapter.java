package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hasbrouckr on 4/29/2016.
 */
public class TeamDetailAdapter extends ArrayAdapter<Game> {

    private Context _context;
    private int _layoutId;
    private Team _team;

    public TeamDetailAdapter(Context context, int layoutId, ArrayList games, Team team){
        super(context, layoutId, games);
        _context = context;
        _layoutId = layoutId;
        _team = team;
        Log.d("SEARCHING FOR ADAPTER", "ENTERED CONSTRUCTOR");
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Game game = getItem(position);

        String oppTeam = game.getOppTeam();
        String startTime = game.getStartTime();
        String loc = game.getGameLoc();
        String score = game.getScore();
        Log.d("ADAPTER VIEW", score + " " + startTime);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(_layoutId, parent,false);
        }


        TextView scoreView = (TextView)view.findViewById(R.id.team_detail_score);
        TextView startTimeView = (TextView)view.findViewById(R.id.team_detail_start_time);
        TextView locView = (TextView)view.findViewById(R.id.team_detail_loctaion);
        TextView oppTeamView = (TextView)view.findViewById(R.id.team_detail_oppTeam);

        scoreView.setText(score);
        startTimeView.setText(startTime);
        locView.setText(loc);
        oppTeamView.setText(_team.getTeamCode() + " vs. " + oppTeam);

        return view;
    }
}
