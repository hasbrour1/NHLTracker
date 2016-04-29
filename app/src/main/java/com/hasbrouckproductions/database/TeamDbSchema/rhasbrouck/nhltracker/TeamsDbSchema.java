package com.hasbrouckproductions.database.TeamDbSchema.rhasbrouck.nhltracker;

/**
 * Created by hasbrouckr on 4/29/2016.
 */
public class TeamsDbSchema {
    public static final class TeamTable{
        public static final String NAME = "teams";

        public static final class Cols{
            public static final String POS = "pos";
            public static final String TEAMNAME = "teamname";
            public static final String TEAMCODE = "teamcode";
            public static final String SELECTED = "selected";
        }
    }
}
