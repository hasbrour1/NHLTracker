package com.hasbrouckproductions.rhasbrouck.nhltracker;

import java.util.ArrayList;

/**
 * Created by hasbrouckr on 4/27/2016.
 * singleton class to hold Team Array
 *
 */
public class Teams {
    private static volatile Teams instance;
    private ArrayList<Team> mTeamList;

    private Teams(){}

    public static Teams getInstance(){
        if(instance == null){
            synchronized (Teams.class){
                if(instance == null){
                    instance = new Teams();
                }
            }
        }
        return instance;
    }

    public Team getTeam(int pos){
        return mTeamList.get(pos);
    }

    public void addTeam(Team newTeam){
        if(mTeamList == null){
            mTeamList = new ArrayList<>();
        }
        mTeamList.add(newTeam);
    }

    public void remove(int position){
        mTeamList.remove(position);
    }

    public int size(){
        return mTeamList.size();
    }

    public ArrayList<Team> getList(){
        return mTeamList;
    }
}
