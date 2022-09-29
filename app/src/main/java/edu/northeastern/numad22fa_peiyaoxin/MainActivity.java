package edu.northeastern.numad22fa_peiyaoxin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickyBtn = findViewById(R.id.clicky_btn);
        Button aboutBtn = findViewById(R.id.about_btn);
        Button linkCollectorBtn = findViewById(R.id.link_collector_btn);

        aboutBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, AboutMeActivity.class)));

        clickyBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ClickyActivity.class)));

        linkCollectorBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LinkCollectorActivity.class)));
 }
}