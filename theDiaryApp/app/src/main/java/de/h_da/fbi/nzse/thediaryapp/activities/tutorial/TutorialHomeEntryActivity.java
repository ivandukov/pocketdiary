package de.h_da.fbi.nzse.thediaryapp.activities.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.activities.HomeScreenActivity;
import de.h_da.fbi.nzse.thediaryapp.R;

public class TutorialHomeEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_home_entry);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setPreviouslyStarted();


        //Button navigates to the next slide

        ImageButton imgBtnNext = findViewById(R.id.btnNext);
        imgBtnNext.setOnClickListener(view -> startActivity(new Intent(this, HomeScreenActivity.class)));


         //Button navigates back to the last slide

        TextView txtBtnBack = findViewById(R.id.txtBtnBackwards);
        txtBtnBack.setOnClickListener(view -> startActivity(new Intent(this, TutorialHomeAchievementActivity.class)));

    }

    private void setPreviouslyStarted() {
        SharedPreferences prefsa = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefsa.edit();
        edit.putBoolean(getString(R.string.pref_previously_started), true);
        edit.apply();
    }

}