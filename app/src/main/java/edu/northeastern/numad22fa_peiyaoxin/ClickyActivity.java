package edu.northeastern.numad22fa_peiyaoxin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ClickyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky);

        Button btnA = findViewById(R.id.buttonA);
        Button btnB = findViewById(R.id.buttonB);
        Button btnC = findViewById(R.id.buttonC);
        Button btnD = findViewById(R.id.buttonD);
        Button btnE = findViewById(R.id.buttonE);
        Button btnF = findViewById(R.id.buttonF);

        TextView pressedTV = findViewById(R.id.TV_Pressed);
        String textString = pressedTV.getText().toString();

        btnA.setOnClickListener(view ->
                pressedTV.setText(textString.replace("-", "A")));
        btnB.setOnClickListener(view ->
                pressedTV.setText(textString.replace("-", "B")));
        btnC.setOnClickListener(view ->
                pressedTV.setText(textString.replace("-", "C")));
        btnD.setOnClickListener(view ->
                pressedTV.setText(textString.replace("-", "D")));
        btnE.setOnClickListener(view ->
                pressedTV.setText(textString.replace("-", "E")));
        btnF.setOnClickListener(view ->
                pressedTV.setText(textString.replace("-", "F")));
    }
}