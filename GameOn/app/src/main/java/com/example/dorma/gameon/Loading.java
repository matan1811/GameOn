package com.example.dorma.gameon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Math.random;

/**
 * Created by dorma on 2017-01-25.
 */

public class Loading extends AppCompatActivity {

    ImageView iv;
    Player oppo = null;
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
            //oppo = new Player("gal", 0, 2, 1, true, new Location(LocationManager.NETWORK_PROVIDER), "player2");
            getRequest(player);
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(player);
            Log.d("beforeadd", oppo.getName());
            players.add(oppo);
            Bundle matchBundle = new Bundle();
//            Log.d("oppo", oppo.getName());
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
            Bundle b = getIntent().getExtras();
            Player player = b.getParcelable("current_player");
            getRequest(player);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Player> players = new ArrayList<Player>();
            players.add(player);
            Log.d("beforeadd", oppo.getName());
            players.add(oppo);
            matchBundle = new Bundle();
            matchBundle.putParcelableArrayList("players", players);
           //matchBundle = checkIfSomeoneAccept();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            an.cancel();
            Intent i = new Intent(Loading.this, Found_opponent.class);
            i.putExtras(matchBundle);
            startActivity(i);
            finish();

        }



    }
    protected RequestQueue getRequest(Player player) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (currentLocation != null) {
            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: " + currentLocation);
        } else {
            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: null");
            return null;
        }

        double lon = currentLocation.getLongitude();  //long
        double lat = currentLocation.getLatitude();  //lat
        //String url = "https://blooming-falls-90763.herokuapp.com/v1/search?lon=3897.98739729&lat=28.2983728739&time=237237239872397&level=4&id=34379473984739";
        String url = "https://gameonserver.herokuapp.com/findOpponent?sport=" + player.geteGames().get(0) + "&level=" + player.getLeague();
        Log.d("checklocation", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);

                        getResponseParser(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //startActivity(new Intent(getApplicationContext(),ErrorPage.class));
                        Log.d("error", "in");
                        Log.d("MainActivityFragment", "Encountered error - " + error);
                    }
                });
        queue.add(jsObjRequest);
        return queue;
    }

    protected void getResponseParser(JSONObject response) {

        try {
            JSONArray jsonarray = response.getJSONArray("opponent");

//            for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(0);
            String username = jsonobject.getString("username");
            int level = jsonobject.getInt("level");
            oppo = new Player(username, 0, level, level, true, new Location(LocationManager.NETWORK_PROVIDER), "player2");
            Log.d("json", oppo.getName());
//            }
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
