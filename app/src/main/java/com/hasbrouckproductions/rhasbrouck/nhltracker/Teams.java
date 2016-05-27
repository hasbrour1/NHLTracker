package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by hasbrouckr on 4/27/2016.
 * singleton class to hold Team Array
 *
 */
public class Teams {
    private static volatile Teams instance;
    private ArrayList<Team> mTeamList;
    private Context mContext;

    private Teams(){
        initialize();
    }

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

    public int size(){
        return mTeamList.size();
    }

    public ArrayList<Team> getList(){
        return mTeamList;
    }

    public void initialize(){
        String[] sTeams = new String[]{"ANA", "Anaheim Ducks", "ARI", "Arizona Coyotes", "BOS", "Boston Bruins",
                "BUF", "Buffalo Sabres", "CAR", "Carolina Hurricanes", "CBJ", "Columbus Blue Jackets", "CGY",
                "Calgary Flames", "CHI", "Chicago Black Hawks", "COL", "Colorado Avalanche", "DAL", "Dallas Stars",
                "DED", "Detroit Red Wings", "EDM", "Edmonton Oilers", "FLA", "Florida Panthers", "LAK", "Los Angeles Knights",
                "MIN", "Minnesota Wild", "MTL", "Montreal Canadians", "NJD", "New Jersey Devils", "NSH", "Nashville Predators",
                "NYI", "New York Islanders", "NYR", "New York Rangers", "PHI", "Philadelphia Flyers",
                "OTT", "Ottawa Senators", "PHI", "Philadelphia Flyers", "PIT", "Pittsburgh Penguins", "SJS", "San Jose Sharks",
                "STL", "St Louis Blues", "TBL", "Tampa Bay Lightning", "TOR", "Toronto Maple Leafs", "VAN", "Vancouver Canucks",
                "WPG", "Winnipeg Jets", "WSH", "Washington Capitals"

        };

        for(int i = 0; i < sTeams.length; i++){
            addTeam(new Team(sTeams[i +1], sTeams[i]));
            i++;
        }

        setIcons();
    }

    private void setIcons(){
        mTeamList.get(getPositionByCode("ANA")).setTeamIcon(R.drawable.ducks);
        mTeamList.get(getPositionByCode("ARI")).setTeamIcon(R.drawable.coyotes);
        mTeamList.get(getPositionByCode("BOS")).setTeamIcon(R.drawable.bruins);
        mTeamList.get(getPositionByCode("BUF")).setTeamIcon(R.drawable.sabres);
        mTeamList.get(getPositionByCode("CAR")).setTeamIcon(R.drawable.hurricanes);
        mTeamList.get(getPositionByCode("CBJ")).setTeamIcon(R.drawable.jackets);
        mTeamList.get(getPositionByCode("CGY")).setTeamIcon(R.drawable.flames);
        mTeamList.get(getPositionByCode("CHI")).setTeamIcon(R.drawable.hawks);
        mTeamList.get(getPositionByCode("COL")).setTeamIcon(R.drawable.avalanche);
        mTeamList.get(getPositionByCode("DAL")).setTeamIcon(R.drawable.stars);
        mTeamList.get(getPositionByCode("DED")).setTeamIcon(R.drawable.wings);
        mTeamList.get(getPositionByCode("EDM")).setTeamIcon(R.drawable.oilers);
        mTeamList.get(getPositionByCode("FLA")).setTeamIcon(R.drawable.panthers);
        mTeamList.get(getPositionByCode("LAK")).setTeamIcon(R.drawable.knights);
        mTeamList.get(getPositionByCode("MIN")).setTeamIcon(R.drawable.wild);
        mTeamList.get(getPositionByCode("MTL")).setTeamIcon(R.drawable.canadians);
        mTeamList.get(getPositionByCode("NDJ")).setTeamIcon(R.drawable.devils);
        mTeamList.get(getPositionByCode("NHS")).setTeamIcon(R.drawable.predators);
        mTeamList.get(getPositionByCode("NYI")).setTeamIcon(R.drawable.islanders);
        mTeamList.get(getPositionByCode("NYR")).setTeamIcon(R.drawable.rangers);
        mTeamList.get(getPositionByCode("OTT")).setTeamIcon(R.drawable.senators);
        mTeamList.get(getPositionByCode("PHI")).setTeamIcon(R.drawable.flyers);
        mTeamList.get(getPositionByCode("PIT")).setTeamIcon(R.drawable.penguins);
        mTeamList.get(getPositionByCode("SJS")).setTeamIcon(R.drawable.sharks);
        mTeamList.get(getPositionByCode("STL")).setTeamIcon(R.drawable.blues);
        mTeamList.get(getPositionByCode("TBL")).setTeamIcon(R.drawable.lightning);
        mTeamList.get(getPositionByCode("TOR")).setTeamIcon(R.drawable.leafs);
        mTeamList.get(getPositionByCode("VAN")).setTeamIcon(R.drawable.canucks);
        mTeamList.get(getPositionByCode("WPG")).setTeamIcon(R.drawable.jets);
        mTeamList.get(getPositionByCode("WSH")).setTeamIcon(R.drawable.capitals);
    }

    public ArrayList<Team> getActiveTeams(){
        ArrayList<Team> activeTeams = new ArrayList<Team>();

        //add teams that are active
        for(int i = 0; i < mTeamList.size(); i++){
            if(mTeamList.get(i).isSelected()){
                activeTeams.add(mTeamList.get(i));
            }
        }

        return activeTeams;
    }

    public void setContext(Context c){
        mContext = c;
    }

    //refreshData will get the data from
    //http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/NYR/2016/04/iphone/clubschedule.json
    //replace NYR with selected team code, and the date with current year and month.
    //use https://www.javacodegeeks.com/2013/10/android-json-tutorial-create-and-parse-json-data.html
    //and http://www.jsoneditoronline.org/?url=http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/NYR/2016/04/iphone/clubschedule.json
    //for help
    public void refreshData(){

        String teamCode;
        String teamUrl;
        String sMonth;
        String sYear;
        Calendar date = Calendar.getInstance();
        SimpleDateFormat month = new SimpleDateFormat("LL");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        sMonth = month.format(date.getTime());
        sYear = year.format(date.getTime());

        /*  Test to make sure they contain correct year and month
        String str = "YEAR: " + sYear + "MONTH: " + sMonth;

        Toast.makeText(HomeActivity.this, str, Toast.LENGTH_LONG).show();
        */

        //get team names and date to get json data
        for(int i = 0; i < mTeamList.size(); i++){
            if(this.getTeam(i).isSelected()){
                teamCode = this.getTeam(i).getTeamCode();
                teamUrl = "http://nhlwc.cdnak.neulion.com/fs1/nhl/league/clubschedule/" + teamCode + "/" +
                        sYear +"/" + sMonth + "/iphone/clubschedule.json";

                // Getting JSON Object
                new JSONParser(mContext, teamCode, mTeamList.get(i)).execute(teamUrl);
            }
        }
    }

    public int getPositionByCode(String code){

        for(int i = 0; i < mTeamList.size(); i++){
            if(mTeamList.get(i).getTeamCode().equals(code))
                return i;
        }

        return 0;
    }
}
