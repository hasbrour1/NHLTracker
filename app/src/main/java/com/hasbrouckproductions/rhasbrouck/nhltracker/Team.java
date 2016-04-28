package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hasbrouckr on 4/22/2016.
 * Holds that data for each individual team.
 * Implements Parcelable in order to save instance
 * state for array of objects in HomeActivity
 *
 */



public class Team implements Parcelable {

    private String teamName;
    private String teamCode;
    private boolean isSelected;

    public Team(String team, String code){
        teamName = team;
        teamCode = code;
        isSelected = false;
    }

    private Team(Parcel in){
        teamName = in.readString();
        teamCode = in.readString();
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamName);
        dest.writeString(teamCode);
    }

    @Override
    public String toString() {
        return teamName + ": " + teamCode;
    }

    public int describeContents(){
        return 0;
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
