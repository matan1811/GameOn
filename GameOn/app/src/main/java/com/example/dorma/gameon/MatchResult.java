package com.example.dorma.gameon;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dorma on 2017-01-03.
 */

public class MatchResult extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_result);
        clearNotification();
        Bundle b = getIntent().getExtras();

        ArrayList<Player> players = b.getParcelableArrayList("players");
        Player player = players.get(0);
        Player oppo = players.get(1);

        Log.d("test_player", player.getUserName());
        Log.d("test_oppo", oppo.getUserName());

        TextView playerNameTextView = (TextView) findViewById(R.id.winlose_leftNameText);
        TextView playerLeagueTextView = (TextView) findViewById(R.id.winlose_leftLevelText);
        TextView oppoNameTextView = (TextView) findViewById(R.id.winlose_rightNameText);
        TextView oppoLeagueTextView = (TextView) findViewById(R.id.winlose_rightLevelText);
        ImageView playerImage = (ImageView) findViewById(R.id.winlose_leftPlayer);
        ImageView oppoImage = (ImageView) findViewById(R.id.winlose_rightplayer);
        ImageView playerWin = (ImageView) findViewById(R.id.winlose_leftWinButton);
        playerWin.setOnClickListener(this);
        ImageView oppoWin = (ImageView) findViewById(R.id.winlose_rightWinButton);
        oppoWin.setOnClickListener(this);
        ImageView endMatch = (ImageView) findViewById(R.id.winlose_endmatch);
        endMatch.setOnClickListener(this);

        playerNameTextView.setText(player.getUserName());
        playerLeagueTextView.setText(player.getLevel() + "");
        oppoNameTextView.setText(oppo.getUserName());
        oppoLeagueTextView.setText(oppo.getLevel() + "");
        int playerImageID = getResources().getIdentifier(player.getPic(), "drawable", getPackageName());
        int oppoImageID = getResources().getIdentifier(oppo.getPic(), "drawable", getPackageName());
        playerImage.setImageResource(playerImageID);
        oppoImage.setImageResource(oppoImageID);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(WantToPlay.NOTIFICATION_ID);
    }
}
