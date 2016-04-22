package com.hasbrouckproductions.rhasbrouck.nhltracker;

/**
 * Created by hasbrouckr on 4/22/2016.
 */



public class Team {

    private String teamName;
    private String teamCode;

    public Team(String team, String code){
        teamName = team;
        teamCode = code;
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
}
