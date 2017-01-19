package com.example.dorma.gameon;

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

    private ImageView tennis, soccer, basketball;
    private ArrayList<ImageView> sport = new ArrayList<>();
    private ImageView lastClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tennis = (ImageView) findViewById(R.id.tennis_image);
        soccer = (ImageView) findViewById(R.id.soccer_image);
        basketball = (ImageView) findViewById(R.id.basketball_image);

        sport.add(tennis);
        sport.add(soccer);
        sport.add(basketball);

        tennis.setOnClickListener(this);
        soccer.setOnClickListener(this);
        basketball.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        resetColor();
        view.setBackgroundColor(Color.parseColor("#4685FF"));
        lastClicked = (ImageView) view;
    }

    private void resetColor(){
            lastClicked.setBackgroundColor(Color.parseColor("#ffffff"));
        }

}
