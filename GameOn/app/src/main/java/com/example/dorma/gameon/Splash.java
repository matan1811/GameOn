package com.example.dorma.gameon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by dorma on 2017-01-03.
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashh);
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);
        /*final ImageView iv = (ImageView) findViewById(R.id.gamelogo);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.abc_fade_out);

        iv.startAnimation(an2);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        startActivity(i);*/

    }
}
