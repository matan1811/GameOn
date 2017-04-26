package com.example.dorma.gameon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by dorma on 2017-01-03.
 */

public class Found_opponent extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_opponent);

        Bundle b = getIntent().getExtras();
        Player player = b.getParcelable("player");
        Player oppo = b.getParcelable("oppo");
        Log.d("test_player", player.getName());
        Log.d("test_oppo", oppo.getName());
    }
}
