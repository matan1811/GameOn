package com.example.dorma.gameon;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

/**
 * Created by matan on 6/12/2017.
 */

public class GameOn extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        createAlarm();
    }

    private void createAlarm() {
        //AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, WantToPlay.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, 1000, pi);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(GameOn.this, WantToPlay.class));
                handler.postDelayed(this, 8000);
            }
        }, 6500);


    }

}
