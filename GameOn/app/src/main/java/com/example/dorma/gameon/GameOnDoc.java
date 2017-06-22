package com.example.dorma.gameon;

import android.content.SharedPreferences;

/**
 * Created by matan on 6/14/2017.
 */

public class GameOnDoc {
    static String userName = null;
    static String phoneNumber = null;
    static String userId = null;
    static int level;

    static void loadGameOnData(SharedPreferences preferences){

        userName = preferences.getString("username", null);
        phoneNumber = preferences.getString("phone_number", null);
        userId = preferences.getString("user_id", null);
        level = preferences.getInt("level", 0);

    }
}
