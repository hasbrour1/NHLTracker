package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    private JSONObject jObj;
    private ArrayList<Game> games;

    public Team(String team, String code){
        teamName = team;
        teamCode = code;
        isSelected = false;
        jObj = new JSONObject();
        games = new ArrayList<>();
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

    public JSONObject getjObj() {
        return jObj;
    }

    public void setjObj(JSONObject jObj) {
        this.jObj = jObj;
        populateGames();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    //Method to take jObj and set ArrayList of Games
    private void populateGames(){
        //remove old game list and populate new one
        games = new ArrayList<>();

        JSONArray gameArray;

        try {
            gameArray = jObj.getJSONArray("games");

            for(int i = 0; i < gameArray.length(); i++){
                JSONObject temJobj = gameArray.getJSONObject(i);
                Game newGame;

                //Check if game is played yet
                if(temJobj.has("score")){
                    newGame = new Game(temJobj.getString("abb"),temJobj.getString("startTime"),temJobj.getString("score"),temJobj.getString("loc"));
                }else{
                    newGame = new Game(temJobj.getString("abb"),temJobj.getString("startTime"), "Not Played Yet",temJobj.getString("loc"));
                }

                games.add(newGame);
            }

        } catch (JSONException e) {
            Log.d("GAME STRING", "ERROR: " + e.getMessage());
        }
    }
}
