package com.hasbrouckproductions.rhasbrouck.nhltracker;

/**
 * Created by Rob on 4/30/2016.
 */
public class Game {
    private String oppTeam;
    private String startTime;
    private String score;
    private String gameLoc;

    public Game(String _oppTeam, String _startTime, String _score, String _gameLoc){
        oppTeam = _oppTeam;
        startTime = _startTime;
        score = _score;
        gameLoc = _gameLoc;
    }

    public String getOppTeam() {
        return oppTeam;
    }

    public void setOppTeam(String oppTeam) {
        this.oppTeam = oppTeam;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGameLoc() {
        return gameLoc;
    }

    public void setGameLoc(String gameLoc) {
        this.gameLoc = gameLoc;
    }
}
