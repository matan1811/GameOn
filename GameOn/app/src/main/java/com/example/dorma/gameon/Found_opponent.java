package com.example.dorma.gameon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dorma on 2017-01-03.
 */

public class Found_opponent extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_match);

        Bundle b = getIntent().getExtras();

        ArrayList<Player> players = b.getParcelableArrayList("players");
        Player player = players.get(0);
        Player oppo = players.get(1);

        Log.d("test_player", player.getUserName());
        Log.d("test_oppo", oppo.getUserName());

        TextView playerNameTextView = (TextView) findViewById(R.id.found_leftNameText);
        TextView playerLeagueTextView = (TextView) findViewById(R.id.found_leftLevelText);
        TextView oppoNameTextView = (TextView) findViewById(R.id.found_rightNameText);
        TextView oppoLeagueTextView = (TextView) findViewById(R.id.found_rightLevelText);
        TextView startTimeTextView = (TextView) findViewById(R.id.found_timeText);
        ImageView playerImage = (ImageView) findViewById(R.id.found_leftPlayer);
        ImageView oppoImage = (ImageView) findViewById(R.id.found_rightplayer);

        playerNameTextView.setText(player.getUserName());
        playerLeagueTextView.setText(player.getLevel() + "");
        oppoNameTextView.setText(oppo.getUserName());
        oppoLeagueTextView.setText(oppo.getLevel() + "");
        startTimeTextView.setText(player.getStartHour() + ":" + player.getStartMinute());
        //endTimeTextView.setText(player.getEndHour() + ":" + player.getEndMinute());
        int playerImageID = getResources().getIdentifier(player.getPic(), "drawable", getPackageName());
        int oppoImageID = getResources().getIdentifier(oppo.getPic(), "drawable", getPackageName());
        playerImage.setImageResource(playerImageID);
        oppoImage.setImageResource(oppoImageID);
    }
}
