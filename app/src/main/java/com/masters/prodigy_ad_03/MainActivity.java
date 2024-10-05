package com.masters.prodigy_ad_03;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int milliseconds = 0; // Store time in milliseconds
    private boolean running = false;
    private Handler handler = new Handler();
    private TextView tvStopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStopWatch = findViewById(R.id.tv_stopWatch);

        // If the app is destroyed and recreated, restore the state
        if (savedInstanceState != null) {
            milliseconds = savedInstanceState.getInt("milliseconds");
            running = savedInstanceState.getBoolean("running");
        }

        runStopWatch();

    }

    public void onClickPlay(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        milliseconds = 0;
        updateStopWatch(0, 0, 0);
    }

    private void runStopWatch() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    milliseconds++;
                }

                // Convert milliseconds to minutes, seconds, and milliseconds format
                int minutes = (milliseconds / 100) / 60;
                int seconds = (milliseconds / 100) % 60;
                int milli = milliseconds % 100;

                updateStopWatch(minutes, seconds, milli);

                // Schedule the next update in 10 milliseconds (for smoother milliseconds updates)
                handler.postDelayed(this, 10);
            }
        });
    }

    // Helper method to update the TextView
    private void updateStopWatch(int minutes, int seconds, int milli) {
        String time = String.format("%02d : %02d : %03d", minutes, seconds, milli);
        tvStopWatch.setText(time);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("milliseconds", milliseconds);
        outState.putBoolean("running", running);
    }
}