package com.example.dorma.gameon;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.dorma.gameon.R.id.soccer_image;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView searchNow;
    private Player player;
    private Button startTimeButton;
    private Button endTimeButton;
    static final int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final ImageView search = (ImageView) findViewById(R.id.search_round);
        //final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate2);
        //search.startAnimation(animation);

        searchNow = (ImageView) findViewById(R.id.search_now);
        searchNow.setOnClickListener(this);
        startTimeButton = (Button) findViewById(R.id.starttime);
        startTimeButton.setOnClickListener(this);
        endTimeButton = (Button) findViewById(R.id.endtime);
        endTimeButton.setOnClickListener(this);
        fetchPlayerData(); //Check for erros
        TextView nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(player.getName());
        TextView leageTextView = (TextView) findViewById(R.id.league);
        leageTextView.setText("league  " + player.getLeague());
    }

  /*  @Override
   public Dialog onCreateDialog(int id) {
         // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(MainActivity.this, this, hour, minute, DateFormat.is24HourFormat(MainActivity.this));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    // Do something with the time chosen by the user
    }*/

    @Override
    public void onClick(View view) {
        //int viewId = view.getId();
        Calendar mcurrentTime;
        mcurrentTime = Calendar.getInstance();
        int hour, minute;
        TimePickerDialog mTimePicker;
        switch(view.getId()){
            case R.id.search_now:
                Log.d("clicked", "clicked");

                if (collectGamesFromUI() == 0) {
                    // print to screen error message
                    return;
                }

                setPlayerTimeFromUI();
                setPlayerLocationFromUI();

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
                break;
            case R.id.starttime:
                //showDialog(DIALOG_ID);
                hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeButton.setText( selectedHour + ":" + selectedMinute);
                        player.setStartHour(selectedHour);
                        player.setStartMinute(selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.endtime:
                //showDialog(DIALOG_ID);
                hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTimeButton.setText( selectedHour + ":" + selectedMinute);
                        player.setEndHour(selectedHour);
                        player.setEndMinute(selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
        }

    }

    private void setPlayerLocationFromUI() {
        // If the user sets the location in the ui, choose this location,
        // else take the current location
    }

    private int collectGamesFromUI(){
        // Iterate over the "balls" check boxes and update
        // the user wanted games
        // return the number of games that was picked
        int counter = 0;
        CheckBox tennisCheckBox = (CheckBox)findViewById(R.id.tennis_image);
        CheckBox basketballCheckBox = (CheckBox)findViewById(R.id.basketball_image);
        CheckBox soccerCheckBox = (CheckBox)findViewById(R.id.soccer_image);
        if(tennisCheckBox.isChecked()){
            counter++;
            Game game = Game.TENNIS;
            player.addGame(game);
        }
        if(basketballCheckBox.isChecked()){
            counter++;
            Game game = Game.BASKETBALL;
            player.addGame(game);
        }
        if(soccerCheckBox.isChecked()){
            counter++;
            Game game = Game.SOCCER;
            player.addGame(game);
        }
        return counter;
    }

    private void setPlayerTimeFromUI() {
        // Gets the time from the ui and update the player object
    }

    private void fetchPlayerData() {
        if (player == null) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bw_basketball);
            player = new Player("matan", 0, 1, 1, true, new Location(LocationManager.NETWORK_PROVIDER), "soccer_image");
        }

        // update player's data

        //init player
        //we need to get current location
    }

    private boolean sendPlayerDataToServer() {
        // This method should be invoked when the current player click on "ready to play button"
        // return true on success
        return true;
    }

    private void getCurrentLocation() {
        // Take from the service
    }





}
