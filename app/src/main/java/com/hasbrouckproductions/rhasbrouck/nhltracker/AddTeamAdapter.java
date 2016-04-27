package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hasbrouckr on 4/27/2016.
 */
public class AddTeamAdapter extends ArrayAdapter<Team> {
    private Context _context;
    int _layoutId;

    public AddTeamAdapter(Context context, int layoutId, ArrayList<Team> teams){
        super(context, layoutId, teams);
        _context = context;
        _layoutId = layoutId;
        Log.d("SEARCHING FOR ADAPTER", "ENTERED CONSTRUCTOR");
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Team teams = getItem(position);

        String teamName = teams.getTeamName();
        String teamCode = teams.getTeamCode();
        Log.d("ADAPTER VIEW", teamCode + " " + teamName);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(_layoutId, parent,false);
        }

        TextView nameView = (TextView)view.findViewById(R.id.adapter_first_line);
        TextView codeView = (TextView)view.findViewById(R.id.adapter_second_line);

        nameView.setText(teamName);
        codeView.setText(teamCode);

        return view;
    }
}
