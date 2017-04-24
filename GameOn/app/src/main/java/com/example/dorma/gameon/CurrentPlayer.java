package com.example.dorma.gameon;

import android.graphics.Bitmap;
import android.location.Location;

/**
 * Created by matan on 4/24/2017.
 */

public class CurrentPlayer extends Player {

    private static CurrentPlayer instance;

    private CurrentPlayer(String name, int id, int exp, int league, Bitmap pic, boolean isFirstPlayer, Location location) {
        super(name, id, exp, league, pic, isFirstPlayer, location);
    }

    private CurrentPlayer() {

    }

    public static CurrentPlayer getInstance() {
        if (instance == null) {
            instance = new CurrentPlayer();
        }

        return instance;
    }
}
