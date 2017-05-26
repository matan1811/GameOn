package com.example.dorma.gameon;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.NumberPicker;

import junit.framework.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        //TextView nameTextView = (TextView) findViewById(R.id.name);
        //ImageView image = (ImageView) findViewById(R.id.player);
        int resID = getResources().getIdentifier(player.getPic(), "drawable", getPackageName());
        //image.setImageResource(resID);
        //nameTextView.setText(player.getName());
        //TextView leageTextView = (TextView) findViewById(R.id.league);
        //leageTextView.setText("league  " + player.getLeague());
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
                mTimePicker = new CustomTimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeButton.setText( selectedHour + ":" + selectedMinute);
                        endTimeButton.setText((selectedHour+1) + ":" + selectedMinute);
                        player.setStartHour(selectedHour);
                        player.setStartMinute(selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.endtime:
                //showDialog(DIALOG_ID);
                mcurrentTime.set(Calendar.MINUTE, player.getStartMinute());
                mcurrentTime.set(Calendar.HOUR_OF_DAY, player.getStartHour()+1);
                hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new CustomTimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        ArrayList<Game> games = new ArrayList<>();
        if(tennisCheckBox.isChecked()){
            counter++;
            games.add(Game.TENNIS);
        }
        if(basketballCheckBox.isChecked()){
            counter++;
            games.add(Game.BASKETBALL);
        }
        if(soccerCheckBox.isChecked()){
            counter++;
            games.add(Game.SOCCER);
        }
        player.seteGames(games);
        return counter;
    }

    private void setPlayerTimeFromUI() {
        // Gets the time from the ui and update the player object
    }

    private void fetchPlayerData() {
        if (player == null) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.bw_basketball);
            player = new Player("matan", 0, 1, 1, true, new Location(LocationManager.NETWORK_PROVIDER), "player1");
        }

        // update player's data

        //init player
        //we need to get current location
    }
    public static int getDrawable(Context context, String name)
    {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        return context.getResources().getIdentifier(name,
                "player1", context.getPackageName());
    }
    private boolean sendPlayerDataToServer() {
        // This method should be invoked when the current player click on "ready to play button"
        // return true on success
        return true;
    }

    private void getCurrentLocation() {
        // Take from the service
    }


    public class CustomTimePickerDialog extends TimePickerDialog {

        private final static int TIME_PICKER_INTERVAL = 30;
        private TimePicker mTimePicker;
        private final OnTimeSetListener mTimeSetListener;

        public CustomTimePickerDialog(Context context, OnTimeSetListener listener,
                                      int hourOfDay, int minute, boolean is24HourView) {
            super(context, TimePickerDialog.THEME_HOLO_LIGHT, null, hourOfDay,
                    minute / TIME_PICKER_INTERVAL, is24HourView);
            mTimeSetListener = listener;
        }

        @Override
        public void updateTime(int hourOfDay, int minuteOfHour) {
            mTimePicker.setCurrentHour(hourOfDay);
            mTimePicker.setCurrentMinute(minuteOfHour / TIME_PICKER_INTERVAL);
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    if (mTimeSetListener != null) {
                        mTimeSetListener.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                                mTimePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);
                    }
                    break;
                case BUTTON_NEGATIVE:
                    cancel();
                    break;
            }
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                Field timePickerField = classForid.getField("timePicker");
                mTimePicker = (TimePicker) findViewById(timePickerField.getInt(null));
                Field field = classForid.getField("minute");

                NumberPicker minuteSpinner = (NumberPicker) mTimePicker
                        .findViewById(field.getInt(null));
                minuteSpinner.setMinValue(0);
                minuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
                List<String> displayedValues = new ArrayList<>();
                for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                    displayedValues.add(String.format("%02d", i));
                }
                minuteSpinner.setDisplayedValues(displayedValues
                        .toArray(new String[displayedValues.size()]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
