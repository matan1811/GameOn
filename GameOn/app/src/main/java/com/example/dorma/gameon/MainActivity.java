package com.example.dorma.gameon;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.dorma.gameon.R.id.soccer_image;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView tennis, soccer, basketball, searchNow;
    private ArrayList<ImageView> sport = new ArrayList<>();
    private ImageView lastClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tennis = (ImageView) findViewById(R.id.tennis_image);
        soccer = (ImageView) findViewById(R.id.soccer_image);
        basketball = (ImageView) findViewById(R.id.basketball_image);
        searchNow = (ImageView) findViewById(R.id.search_now);

        sport.add(tennis);
        sport.add(soccer);
        sport.add(basketball);

        tennis.setOnClickListener(this);
        soccer.setOnClickListener(this);
        basketball.setOnClickListener(this);
        searchNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == tennis.getId()|| viewId == soccer.getId() || viewId == basketball.getId()){
            resetColor();
            view.setBackgroundColor(Color.parseColor("#4685FF"));
            lastClicked = (ImageView) view;
        } else if (viewId == searchNow.getId()){
            Intent i=new Intent(MainActivity.this, Loading.class);
            startActivity(i);
        }

    }

    private void resetColor(){
            lastClicked.setBackgroundColor(Color.parseColor("#ffffff"));
        }

}
