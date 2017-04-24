package com.example.dorma.gameon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by dorma on 2017-01-25.
 */

public class Loading extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);
        final ImageView iv = (ImageView) findViewById(R.id.loadingicon);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.whisle);
        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //finish();
                mp.start();
                Intent i=new Intent(Loading.this, Found_opponent.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void checkIfSomeoneAccept() {
        Bundle b = getIntent().getExtras();
        Player player = b.getParcelable("current_player");
        // Check with the server if someone accept the request from player
    }
}
