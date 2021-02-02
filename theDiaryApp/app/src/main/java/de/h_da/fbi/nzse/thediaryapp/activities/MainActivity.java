package de.h_da.fbi.nzse.thediaryapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.R;
import de.h_da.fbi.nzse.thediaryapp.activities.welcome.WelcomeAActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide top bar
        Objects.requireNonNull(getSupportActionBar()).hide();


        Handler handler = new Handler();
        handler.postDelayed(this::checkAlreadyStarted, 1300);

    }

    /**
     * Checks if the app was already started.
     * If it was already started it goes directly to the homescreen.
     * If it was not started it goes to the welcome+tutorial screen
     */
    private void checkAlreadyStarted() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean alreadyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if (alreadyStarted) {

            startActivity(new Intent(this, HomeScreenActivity.class));

        } else {

            startActivity(new Intent(this, WelcomeAActivity.class));
        }

    }

}