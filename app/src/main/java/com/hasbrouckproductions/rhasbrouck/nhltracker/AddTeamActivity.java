package com.hasbrouckproductions.rhasbrouck.nhltracker;

/*

    AddTeamActivity will take the team name and 3 letter code and send it with an intent to
    the HomeActivity

    Eventually validate team codes/names before sending

 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTeamActivity extends AppCompatActivity {

    public Button mEnterButton;
    public EditText mTeamNameEdit;
    public EditText mTeamCodeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        //Initialize Edit Texts
        mTeamNameEdit = (EditText)findViewById(R.id.teamNameEditText);
        mTeamCodeEdit = (EditText)findViewById(R.id.teamCodeEditText);

        //add Enter Button Listener
        mEnterButton = (Button)findViewById(R.id.enterButton);
        mEnterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String teamName, teamCode;

                //Check if both edit texts are filled and send in intent
                teamName = mTeamNameEdit.getText().toString();
                teamCode = mTeamCodeEdit.getText().toString();

                //check team code is only 3
                if(teamCode.length() != 3){
                    Toast.makeText(v.getContext(), "Error: Team Code must be 3 letters", Toast.LENGTH_SHORT).show();
                }else{
                    //make sure team name is filled
                    if(teamName.equals("")){
                        Toast.makeText(v.getContext(), "Error: Please Enter Team Name", Toast.LENGTH_SHORT).show();
                    }else {
                        //Send intent back to HomeActivity
                        Intent intent = new Intent(AddTeamActivity.this,HomeActivity.class);
                        intent.putExtra("team_name", teamName);
                        intent.putExtra("team_code", teamCode);
                        startActivity(intent);

                    }
                }
            }
        });
    }
}
