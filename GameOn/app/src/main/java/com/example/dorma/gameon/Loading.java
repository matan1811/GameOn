package com.example.dorma.gameon;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

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
            Player oppo = new Player("gal", 0, 2, 1, true, new Location(LocationManager.NETWORK_PROVIDER), "player2");
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(player);
            players.add(oppo);
            Bundle matchBundle = new Bundle();
            Log.d("oppo", oppo.getName());
            matchBundle.putParcelableArrayList("players", players);
            //matchBundle.putParcelable("players", players);
           // matchBundle.putParcelable("player", player);

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
    protected RequestQueue getRequest(Player player) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(this);
        double alt = player.getLocation().getAltitude();
        double lan = player.getLocation().getLatitude();
        String url = "https://blooming-falls-90763.herokuapp.com/v1/search?lon=3897.98739729&lat=28.2983728739&time=237237239872397&level=4&id=34379473984739";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("error", "in");
                        Log.d("MainActivityFragment", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        return queue;
    }
}
