package com.example.dorma.gameon;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by dorma on 2017-01-03.
 */

public class Splash extends AppCompatActivity {
    final int SPLASH_DISPLAY_LENGTH = 3000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashh);
        final ImageView iv = (ImageView) findViewById(R.id.gamelogo);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_in);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);
        //iv.startAnimation(an);
        //iv.startAnimation(an2);

         /* New Handler to start the Menu-Activity
 * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
        /* Create an Intent that will start the Menu-Activity. */

                //sent intent to main screen. usded by:  new Intent(Splash.this,MapsActivity.class);
//                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();
                startNextActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private void startNextActivity() {
       /* New Handler to start the Menu-Activity
        * and close this Splash-Screen after some seconds.
        */
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

        /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
                GameOnDoc.loadGameOnData(settings);
                if (settings.contains("user_id")) {

                    //sent intent to main screen. use by:  new Intent(Splash.this,MapsActivity.class);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    //the user DID NOT LOGIN
                    // then we will run the service after the first login
                    Intent mainIntent = new Intent(Splash.this, SignUp.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }
            }
        }, 1500);
    }
}
