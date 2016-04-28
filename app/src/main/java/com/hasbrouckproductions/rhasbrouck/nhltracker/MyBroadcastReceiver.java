package com.hasbrouckproductions.rhasbrouck.nhltracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private Teams teams;

    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        teams = Teams.getInstance();
        teams.refreshData();
    }
}
