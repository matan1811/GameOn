package com.example.dorma.gameon;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.dorma.gameon.R.id.soccer_image;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView searchNow;
    private CurrentPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final ImageView search = (ImageView) findViewById(R.id.search_round);
        //final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate2);
        //search.startAnimation(animation);

        searchNow = (ImageView) findViewById(R.id.search_now);

        searchNow.setOnClickListener(this);

        fetchPlayerData(); //Check for erros
    }

    @Override
    public void onClick(View view) {
        //int viewId = view.getId();

        if (collectGamesFromUI() == 0) {
            // print to screen error message
            return;
        }

        setPlayerTimeFromUI();
        setPlayerLocationFromUI();
        player.setFirstPlayer(true);

        if ( ! sendPlayerDataToServer()) {
            // print error message, maybe retry
            // or try again
            return;
        }

        Bundle b = new Bundle();
        b.putParcelable("current_player", player);

        Intent i=new Intent(MainActivity.this, Loading.class);
        i.putExtras(b);
        startActivity(i);

    }

    private void setPlayerLocationFromUI() {
        // If the user sets the location in the ui, choose this location,
        // else take the current location
    }

    private int collectGamesFromUI(){
        // Iterate over the "balls" check boxes and update
        // the user wanted games
        // return the number of games that was picked

        return 0;
    }

    private void setPlayerTimeFromUI() {
        // Gets the time from the ui and update the player object
    }

    private void fetchPlayerData() {
        if (player == null) {
            // create player
        }

        // update player's data

        //init player
        //we need to get current location
    }

    private boolean sendPlayerDataToServer() {
        // This method should be invoked when the current player click on "ready to play button"
        // return true on success
        return false;
    }

    private void getCurrentLocation() {
        // Take from the service
    }





}
