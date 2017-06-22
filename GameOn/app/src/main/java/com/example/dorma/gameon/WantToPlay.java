package com.example.dorma.gameon;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
import java.util.List;

public class WantToPlay extends Service {

    public static final int REQUEST_CODE_START_PLAYING = 5;
    public static final int NOTIFICATION_ID = 15;
//    private int isModified;

    public WantToPlay() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent i = new Intent(this, MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
        if (isForeground("com.example.dorma.gameon.Loading")) return super.onStartCommand(intent, flags, startId);
        SharedPreferences settings = getSharedPreferences("UserInfo", MODE_PRIVATE);
        GameOnDoc.loadGameOnData(settings);
        String username = settings.getString("username", null);
        String id = settings.getString("user_id", null);
        int level = settings.getInt("level", 0);
        Log.d("userninfo", username + " " + id + " " + level);
        Player player = new Player(username, id, level, Game.BASKETBALL, username + "pic");
        // TODO: check if a new player has joined
        checkIfItsOn(player);
//        Log.d("modified", isModified + "");
//        if (isModified == 1) {
//            isModified = 0;
//            getOppo(player);
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification(Player me, Player other) {

        final Intent intent = new Intent(this, MatchResult.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle matchBundle = new Bundle();
        ArrayList<Player> players = new ArrayList<Player>();

        players.add(me);
        Log.d("show: me", me.getUserName());
        players.add(other);
        Log.d("show: other", other.getUserName());

        if (me == other) {
            Log.d("show", "equals");
        }

        matchBundle.putParcelableArrayList("players", players);
        intent.putExtras(matchBundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_START_PLAYING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.loadicon)
                        .setContentTitle(other.getUserName() + "Wants to play")
                        .setContentText("Click here to play with him!")
                        .setSound(Uri.parse("android.resource://com.example.dorma.gameon/" + R.raw.whisle))
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    protected RequestQueue checkIfItsOn(final Player player) {
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
        String url = "https://gameonserver.herokuapp.com/findopengame?game=" + player.geteGame() + "&level=" + player.getLevel() + "&user=" + player.getId() + "&username=" + player.getUserName();
        Log.d("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);
                        try {
                            if (response.getInt("nModified") == 1) getOppo(player);
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

        final RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        String url = "https://gameonserver.herokuapp.com/getplayerforjoin?user=" + player.getId();
        Log.d("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", "in");
                        Log.d("MainActivityFragment", "Response - " + response);

                        Player other = getResponseParser(response);
                        showNotification(player, other);
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

    protected Player getResponseParser(JSONObject response) {

        try {
            JSONArray jsonarray = response.getJSONArray("game");
            JSONObject jsonobject = jsonarray.getJSONObject(0);
            String userName = jsonobject.getString("usernameinit");
            String id = jsonobject.getString("userinit");
            int level = jsonobject.getInt("level");
            String game = jsonobject.getString("game");
            Log.d("oppo_username", userName);
            Log.d("oppo_id", id);
            Log.d("oppo_level", level + "");
            Log.d("oppo_game", game);


            return new Player(userName, id, level,Game.valueOf(game), userName + "pic");

            //oppo = new Player(username, id, level,game, username + "pic");
            //oppo = new Player("assaf", "44444", 4, Game.BASKETBALL,"assafpic");
            //Log.d("json", oppo.getUserName());
        } catch (org.json.JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isForeground(String myPackage) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        Log.d("runningNow: ", componentInfo.getClassName());
        return componentInfo.getClassName().equals(myPackage);
    }
}
