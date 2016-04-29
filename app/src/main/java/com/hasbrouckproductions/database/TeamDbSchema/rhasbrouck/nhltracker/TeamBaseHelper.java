package com.hasbrouckproductions.database.TeamDbSchema.rhasbrouck.nhltracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hasbrouckproductions.database.TeamDbSchema.rhasbrouck.nhltracker.TeamsDbSchema.TeamTable;
import com.hasbrouckproductions.rhasbrouck.nhltracker.Team;

/**
 * Created by hasbrouckr on 4/29/2016.
 */
public class TeamBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "teambase.db";
    public TeamBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TeamTable.NAME + "(" +
                TeamTable.Cols.POS + ", " +
                TeamTable.Cols.TEAMNAME + ", " +
                TeamTable.Cols.TEAMCODE + ", " +
                TeamTable.Cols.SELECTED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
