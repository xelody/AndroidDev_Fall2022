package edu.northeastern.numad22fa_peiyaoxin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import com.google.android.material.snackbar.Snackbar;

public class FindPrimeActivity extends AppCompatActivity {
    private static final String TAG = "FindPrimeActivity";
    private static final int DEFAULT_START = 3;
    private Handler textHandler = new Handler();
    private Thread findPrimeThread;
    private boolean isInProgress;
    private int currentNumber;
    private int currentPrime;
    TextView currNumberText;
    TextView currPrimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_prime);
        isInProgress = false;
        currentNumber = DEFAULT_START;
        currentPrime = DEFAULT_START;
        if (savedInstanceState != null) {
            isInProgress = savedInstanceState.getBoolean("isInProgress");
            currentNumber = savedInstanceState.getInt("currentNumber");
            currentPrime = savedInstanceState.getInt("currentPrime");
        }

        currNumberText = findViewById(R.id.tvCurrentNumber);
        currNumberText.setText(String.valueOf(currentNumber));
        currPrimeText = findViewById(R.id.tvCurrentPrime);
        currPrimeText.setText(String.valueOf(currentPrime));
        Button findBtn = findViewById(R.id.activityPrimesBtn);
        Button stopBtn = findViewById(R.id.terminateBtn);
        findBtn.setOnClickListener(this::startPrimeSearch);
        stopBtn.setOnClickListener(this::stopPrimeSearch);

        if (isInProgress) {
            findPrimeThread = new Thread(new FindPrimeThread());
            findPrimeThread.start();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("isInProgress", isInProgress);
        outState.putInt("currentNumber", currentNumber);
        outState.putInt("currentPrime", currentPrime);
        super.onSaveInstanceState(outState);
    }

    public void startPrimeSearch() {
        if (isInProgress) return;
        findPrimeThread = new Thread(new FindPrimeThread());
        findPrimeThread.start();
    }

    public void startPrimeSearch(View view) {
        startPrimeSearch();
    }

    public void stopPrimeSearch(View view) {
        if (isInProgress) {
            findPrimeThread.interrupt();
        }
    }

    @Override
    public void onBackPressed() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.findPrimeLayout), "Confirm exit?", Snackbar.LENGTH_LONG)
                .setAction("YES", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(FindPrimeActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });

        snackbar.show();
    }

    class FindPrimeThread implements Runnable {
        @Override
        public void run() {
            int current = isInProgress ? currentNumber : DEFAULT_START;
            while(!Thread.currentThread().isInterrupted()) {
                final int candidate = current;
                final String candidateStr = String.valueOf(current);
                textHandler.post(() -> {
                    isInProgress = true;
                    currNumberText.setText(candidateStr);
                    currentNumber = candidate;
                    if (isPrime(candidate)) {
                        currPrimeText.setText(candidateStr);
                        currentPrime = candidate;
                    }
                });

                current = current + 2;
                if (current >= Integer.MAX_VALUE - 1) {
                    current = 3;
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    textHandler.post(() -> {
                        isInProgress = false;
                    });
                }
            }
        }

        private boolean isPrime(int num) {
            if (num <= 1) return false;

            for (int i = 2; i <= num/2; i++) {
                if (num % i == 0) return false;
            }

            return true;
        }
    }
}