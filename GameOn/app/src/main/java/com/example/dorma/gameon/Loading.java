package com.example.dorma.gameon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.dorma.gameon.R.drawable.player;

/**
 * Created by dorma on 2017-01-25.
 */

public class Loading extends AppCompatActivity {

    private Bundle matchBundle = null;
    ImageView iv;
    Player oppo = null;
    boolean notJoin = true;
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

//        private Bundle checkIfSomeoneAccept() {
//            Bundle b = getIntent().getExtras();
//            Player player = b.getParcelable("current_player");
//            //oppo = new Player("gal", 0, 2, 1, true, new Location(LocationManager.NETWORK_PROVIDER), "player2");
//            openNewGame(player);
//            ArrayList<Player> players = new ArrayList<Player>();
//            players.add(player);
//            Log.d("beforeadd", oppo.getUserName());
//            players.add(oppo);
//            Bundle matchBundle = new Bundle();
////            Log.d("oppo", oppo.getName());
//            matchBundle.putParcelableArrayList("players", players);
//            //matchBundle.putParcelable("players", players);
//            // matchBundle.putParcelable("player", player);
//
//            return matchBundle;
//        }

        @Override
        protected void onPreExecute() {
            an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate2);
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

            openNewGame(player);
            while (notJoin){
                checkIfJoinGame(player);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getOppo(player);
            while(matchBundle == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
           //matchBundle = checkIfSomeoneAccept();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            an.cancel();
            Intent i = new Intent(Loading.this, MatchResult.class);
            i.putExtras(matchBundle);
            startActivity(i);
            finish();

        }



    }
    protected RequestQueue openNewGame(Player player) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
//        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (currentLocation != null) {
//            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: " + currentLocation);
//        } else {
//            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: null");
//            return null;
//        }

        //String url = "https://blooming-falls-90763.herokuapp.com/v1/search?lon=3897.98739729&lat=28.2983728739&time=237237239872397&level=4&id=34379473984739";
        String url = "https://gameonserver.herokuapp.com/opennewgame?game=" + player.geteGame() + "&level=" + player.getLevel() + "&user=" + player.getId() + "&username=" + player.getUserName();
        Log.d("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);

                        //getResponseParser(response);
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
    protected RequestQueue checkIfJoinGame(Player player) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
//        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (currentLocation != null) {
//            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: " + currentLocation);
//        } else {
//            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: null");
//            return null;
//        }

        //String url = "https://blooming-falls-90763.herokuapp.com/v1/search?lon=3897.98739729&lat=28.2983728739&time=237237239872397&level=4&id=34379473984739";
        String url = "https://gameonserver.herokuapp.com/checkjoin?game=" + player.geteGame() + "&level=" + player.getLevel() + "&user=" + player.getId();
        Log.d("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);
                        try {
                            if (response.getInt("nModified") == 1) notJoin = false;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    protected RequestQueue getOppo(final Player player) {
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(getBaseContext());
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return null;
//        }
//        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (currentLocation != null) {
//            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: " + currentLocation);
//        } else {
//            Log.d("LocationUpdateService", "getDeviceLocationForLocationService: null");
//            return null;
//        }

        //String url = "https://blooming-falls-90763.herokuapp.com/v1/search?lon=3897.98739729&lat=28.2983728739&time=237237239872397&level=4&id=34379473984739";
        String url = "https://gameonserver.herokuapp.com/getplayerforinit?user=" + player.getId();
        Log.d("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);

                        getResponseParser(response);
                        ArrayList<Player> players = new ArrayList<Player>();
                        players.add(player);
                        players.add(oppo);
                        matchBundle = new Bundle();
                        matchBundle.putParcelableArrayList("players", players);
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
            JSONArray jsonarray = response.getJSONArray("game");

//            for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(0);
            String username = jsonobject.getString("usernameother");
            String id = jsonobject.getString("userother");
            int level = jsonobject.getInt("level");
            Game game = Game.valueOf(jsonobject.getString("game"));
            Log.d("oppo_username", username);
            Log.d("oppo_id", id);
            Log.d("oppo_level", level + "");
            Log.d("oppo_game", game.toString());
            oppo = new Player(username, id, level,game, username + "pic");
            Log.d("json", oppo.getUserName());


//            }
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
