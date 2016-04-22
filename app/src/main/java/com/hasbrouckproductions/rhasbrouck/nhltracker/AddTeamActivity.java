package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*

    AddTeamActivity will take the team name and 3 letter code and send it with an intent to
    the HomeActivity

    Eventually validate team codes/names before sending

 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTeamActivity extends AppCompatActivity {

    public Button mEnterButton;
    public EditText mTeamNameEdit;
    public EditText mTeamCodeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        //add Enter Button Listener
        mEnterButton = (Button)findViewById(R.id.enterButton);
        mEnterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: Check if both edit texts are filled and send in intent
            }
        });
    }
}
