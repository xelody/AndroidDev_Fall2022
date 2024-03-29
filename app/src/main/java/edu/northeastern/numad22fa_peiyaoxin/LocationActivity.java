package edu.northeastern.numad22fa_peiyaoxin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class LocationActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private double lastLatitude;
    private double lastLongitude;
    private double distance = 0;
    private boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
        }

        if (savedInstanceState != null) {
            lastLatitude = savedInstanceState.getDouble("lastLatitude");
            lastLongitude = savedInstanceState.getDouble("lastLongitude");
            distance = savedInstanceState.getDouble("distance");
            firstTime = savedInstanceState.getBoolean("firstTime");
        }

        getLocation();

        Button btn_resetDistance = findViewById(R.id.btn_resetDistance);
        btn_resetDistance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                distance = 0;
                TextView tvDistance = findViewById(R.id.tv_totalDistance);
                tvDistance.setText("Total distance traveled: " + distance + " km");
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager =
                    (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    5,
                    LocationActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putDouble("lastLatitude", lastLatitude);
        outState.putDouble("lastLongitude", lastLongitude);
        outState.putDouble("distance", distance);
        outState.putBoolean("firstTime", firstTime);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.locationLayout), "Total Distance will be lost. Confirm exit?", Snackbar.LENGTH_LONG)
                .setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(LocationActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });

        snackbar.show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (firstTime) {
            firstTime = false;
            lastLatitude = location.getLatitude();
            lastLongitude = location.getLongitude();
        }

        distance += calculateDistance(location.getLatitude(), location.getLongitude(),
                                      lastLatitude, lastLongitude);
        TextView tvDistance = findViewById(R.id.tv_totalDistance);
        String newDistance = String.format("Total distance traveled %.5f km", distance);
        tvDistance.setText(newDistance);

        lastLatitude = location.getLatitude();
        TextView tvLatitude = findViewById(R.id.tv_latitude);
        tvLatitude.setText("Latitude: " + location.getLatitude());

        lastLongitude = location.getLongitude();
        TextView tvLongitude = findViewById(R.id.tv_longitude);
        tvLongitude.setText("Longitude: " + location.getLongitude());
    }

    public static double calculateDistance(double currentLatitude, double currentLongitude,
                                  double previousLatitude, double previousLongitude)
    {
        currentLatitude = Math.toRadians(currentLatitude);
        currentLongitude = Math.toRadians(currentLongitude);
        previousLatitude = Math.toRadians(previousLatitude);
        previousLongitude = Math.toRadians(previousLongitude);

        // Haversine formula https://en.wikipedia.org/wiki/Haversine_formula
        double latitudeDelta = currentLatitude - previousLatitude;
        double longitudeDelta = currentLongitude - previousLongitude;
        double a = Math.pow(Math.sin(latitudeDelta / 2), 2)
                + Math.cos(currentLatitude) * Math.cos(previousLatitude)
                * Math.pow(Math.sin(longitudeDelta / 2),2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double earthRadius = 6371 /* km */;

        // calculate the result
        return(c * earthRadius);
    }
}