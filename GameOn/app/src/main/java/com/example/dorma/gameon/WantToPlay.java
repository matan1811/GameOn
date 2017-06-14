package com.example.dorma.gameon;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class WantToPlay extends Service {

    public static final int REQUEST_CODE_START_PLAYING = 5;
    public static final int NOTIFICATION_ID = 15;

    public WantToPlay() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent i = new Intent(this, MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);

        // TODO: check if a new player has joined

        showNotification();

        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE_START_PLAYING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.loadicon)
                        .setContentTitle("Want to have a game?")
                        .setContentText("Click here if you want!")
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
}
