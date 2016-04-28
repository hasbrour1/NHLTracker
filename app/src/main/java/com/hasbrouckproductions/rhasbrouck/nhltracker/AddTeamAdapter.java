package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(final int position, View view, ViewGroup parent) {
        Team teams = getItem(position);

        final String teamName = teams.getTeamName();
        String teamCode = teams.getTeamCode();
        boolean isSelected = teams.isSelected();
        Log.d("ADAPTER VIEW", teamCode + " " + teamName);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(_layoutId, parent,false);
        }

        TextView nameView = (TextView)view.findViewById(R.id.addTeam_adapter_first_line);
        TextView codeView = (TextView)view.findViewById(R.id.addTeam_adapter_second_line);
        CheckBox cb = (CheckBox)view.findViewById(R.id.teamCheckBox);

        cb.setChecked(isSelected);
        nameView.setText(teamName);
        codeView.setText(teamCode);


        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Teams mTeams = Teams.getInstance();
                DisplayToast(mTeams.getTeam(position).getTeamName() + " is checked");
                mTeams.getTeam(position).setSelected(isChecked);
            }
        });


        return view;
    }

    private void DisplayToast(String msg) {
        Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
    }
}
