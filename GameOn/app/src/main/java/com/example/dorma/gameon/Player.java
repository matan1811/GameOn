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
    private Bitmap pic;
    private boolean isFirstPlayer;
    private Location location;
    private ArrayList<Game> eGames = new ArrayList<>();
    private Date time;
    private String picPath;

    public Player(){

    }

    public Player(String name, int id, int exp, int league, boolean isFirstPlayer, Location location, String picPath) {
        this.name = name;
        this.id = id;
        this.exp = exp;
        this.league = league;
        this.isFirstPlayer = isFirstPlayer;
        this.location = location;
        this.picPath = picPath;
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
        dest.writeByte((byte) (this.isFirstPlayer ? 1 : 0));
        location.writeToParcel(dest, flags);
        dest.writeList(this.eGames);

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

    }

    public void populateFromJson(String json){
        // Get a json string and init this user
    }

    public static Player getPlayerFromJson(String json) {
        Player player = new Player();
        player.populateFromJson(json);
        return player;
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

    public String gePic() {
        return picPath;
    }

    public void setPic(String picPath) {
        this.picPath = picPath;
    }
}
