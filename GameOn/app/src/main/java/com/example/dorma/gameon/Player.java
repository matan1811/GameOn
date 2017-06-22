package com.example.dorma.gameon;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by matan on 4/24/2017.
 */

public class Player implements Parcelable {
    private String userName;
    private String id;
    //private int exp;
    private int level;
    //private Bitmap pic;
    //private boolean isFirstPlayer;
    //private Location location;
    private Game game;
    //private Date time;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String picPath;
    static String phoneNumber;

    public Player(){

    }

    public void setStartHour(int hour) {
        this.startHour = hour;
    }

    public void setStartMinute(int minute) {
        this.startMinute = minute;
    }

    public void setEndHour(int hour) {
        this.endHour = hour;
    }

    public void setEndMinute(int minute) {
        this.endMinute = minute;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Player(String userName, String id, int level, Game game, String picPath) {
        this.userName = userName;
        this.id = id;
        this.level = level;
        this.picPath = picPath;
        this.game = Game.BASKETBALL;
        this.startHour = 0;
        this.startMinute = 0;
        this.endHour = 0;
        this.endMinute = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.id);
        dest.writeInt(this.level);
        dest.writeString(this.game.toString());
        dest.writeInt(this.startHour);
        dest.writeInt(this.startMinute);
        dest.writeInt(this.endHour);
        dest.writeInt(this.endMinute);
        dest.writeString(this.picPath);

        //...

    }

    public static final Parcelable.Creator<Player> CREATOR
            = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    private Player(Parcel in) {
        this.userName = in.readString();
        this.id = in.readString();
        this.level = in.readInt();
        this.game = Game.valueOf(in.readString());
        this.startHour = in.readInt();
        this.startMinute = in.readInt();
        this.endHour = in.readInt();
        this.endMinute = in.readInt();
        this.picPath = in.readString();

    }

    public Game geteGame() {
        return game;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public String getPicPath() {
        return picPath;
    }

    public static Creator<Player> getCREATOR() {
        return CREATOR;
    }

    public void populateFromJson(String json){
        // Get a json string and init this user
    }

    public static Player getPlayerFromJson(String json) {
        Player player = new Player();
        player.populateFromJson(json);
        return player;
    }
    public void seteGame(Game game){ game = game; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLeague(int league) {
        this.level = league;
    }

    public String getPic() {
        return picPath;
    }

    public void setPic(String picPath) {
        this.picPath = picPath;
    }
}
