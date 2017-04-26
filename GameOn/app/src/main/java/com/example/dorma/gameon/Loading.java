package com.example.dorma.gameon;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by dorma on 2017-01-25.
 */

public class Loading extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        iv = (ImageView) findViewById(R.id.loadingicon);
        new MyTask().execute();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

        // Check with the server if someone accept the request from player

    private class MyTask extends AsyncTask<String, Void, Void> {

        private Animation an;
        private Bundle matchBundle;


        private Bundle checkIfSomeoneAccept() {
            Bundle b = getIntent().getExtras();
            Player player = b.getParcelable("current_player");
            Player oppo = new Player("gal", 0, 2, 1, true, new Location(LocationManager.NETWORK_PROVIDER), "bw_basketball");

            Bundle matchBundle = new Bundle();

            matchBundle.putParcelable("player", player);
            matchBundle.putParcelable("oppo", oppo);

            return matchBundle;
        }

        @Override
        protected void onPreExecute() {
            an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
            iv.startAnimation(an);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... params) {
            // Add request from server logic
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            matchBundle = checkIfSomeoneAccept();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            an.cancel();
            Intent i=new Intent(Loading.this, Found_opponent.class);
            i.putExtras(matchBundle);
            startActivity(i);
        }
    }
}
