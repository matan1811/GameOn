package com.example.dorma.gameon;

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
    private String name;
    private int id;
    private int exp;
    private int league;
    //private Bitmap pic;
    private boolean isFirstPlayer;
    private Location location;
    private ArrayList<Game> eGames = new ArrayList<>();
    //private Date time;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private String picPath;

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

    public Player(String name, int id, int exp, int league, boolean isFirstPlayer, Location location, String picPath) {
        this.name = name;
        this.id = id;
        this.exp = exp;
        this.league = league;
        this.isFirstPlayer = isFirstPlayer;
        this.location = location;
        this.picPath = picPath;
        this.eGames = new ArrayList<Game>();
        eGames.add(Game.BASKETBALL);
        eGames.add(Game.SOCCER);
        eGames.add(Game.TENNIS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeInt(this.exp);
        dest.writeInt(this.league);
        //pic.writeToParcel(dest,flags);
        dest.writeByte((byte) (this.isFirstPlayer ? 1 : 0));
        location.writeToParcel(dest, flags);
        dest.writeList(this.eGames);
        //dest.writeInt(this.startHour);
        //dest.writeInt(this.startMinute);
        //dest.writeInt(this.endHour);
        //dest.writeInt(this.endMinute);
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
        this.name = in.readString();
        this.id = in.readInt();
        this.exp = in.readInt();
        this.league = in.readInt();
        this.isFirstPlayer = in.readByte() != 0;
        this.location = Location.CREATOR.createFromParcel(in);
        this.eGames = in.readArrayList(Game.class.getClassLoader());
        this.picPath = in.readString();

    }

    public void populateFromJson(String json){
        // Get a json string and init this user
    }

    public static Player getPlayerFromJson(String json) {
        Player player = new Player();
        player.populateFromJson(json);
        return player;
    }
    public void seteGames(ArrayList<Game> games){ eGames = games; }

    public void addGame(Game game){eGames.add(game);}

    public String getGames() {
        return eGames.toString();
    }

    public boolean isFirstPlayer() {
        return isFirstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        isFirstPlayer = firstPlayer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public String getPic() {
        return picPath;
    }

    public void setPic(String picPath) {
        this.picPath = picPath;
    }
}
