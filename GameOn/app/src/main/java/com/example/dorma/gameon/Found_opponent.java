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
        setContentView(R.layout.found_opponent);

        Bundle b = getIntent().getExtras();
        //Player oppo = b.getParcelable("oppo");
        //Player player = b.getParcelable("player");
        ArrayList<Player> players = b.getParcelableArrayList("players");
        Player player = players.get(0);
        Player oppo = players.get(1);
        Log.d("test_player", player.getPic());
        Log.d("test_oppo", oppo.getPic());
        TextView playerNameTextView = (TextView) findViewById(R.id.player_name);
        TextView playerLeagueTextView = (TextView) findViewById(R.id.player_league);
        TextView oppoNameTextView = (TextView) findViewById(R.id.oppo_name);
        TextView oppoLeagueTextView = (TextView) findViewById(R.id.oppo_league);
        TextView startTimeTextView = (TextView) findViewById(R.id.starttime);
        TextView endTimeTextView = (TextView) findViewById(R.id.endtime);
        ImageView playerImage = (ImageView) findViewById(R.id.player_image);
        ImageView oppoImage = (ImageView) findViewById(R.id.oppo_image);

        playerNameTextView.setText(player.getName());
        playerLeagueTextView.setText("league  " + player.getLeague());
        oppoNameTextView.setText(oppo.getName());
        oppoLeagueTextView.setText("league  " + oppo.getLeague());
        startTimeTextView.setText(player.getStartHour() + ":" + player.getStartMinute());
        endTimeTextView.setText(player.getEndHour() + ":" + player.getEndMinute());
        int playerImageID = getResources().getIdentifier(player.getPic(), "drawable", getPackageName());
        int oppoImageID = getResources().getIdentifier(oppo.getPic(), "drawable", getPackageName());
        playerImage.setImageResource(playerImageID);
        oppoImage.setImageResource(oppoImageID);
    }
}
