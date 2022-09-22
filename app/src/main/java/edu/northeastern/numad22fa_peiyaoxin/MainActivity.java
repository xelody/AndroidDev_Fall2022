package edu.northeastern.numad22fa_peiyaoxin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button about_btn = findViewById(R.id.about_btn);

        about_btn.setOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Peiyao Xin\nxin.pe@northeastern.edu",
                        Toast.LENGTH_LONG).show());

        Button clickyBtn = (Button) findViewById(R.id.clicky_btn);

        clickyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ClickyActivity.class));
            }
        });
     }
}