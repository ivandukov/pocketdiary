package de.h_da.fbi.nzse.thediaryapp.activities.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.R;

public class TutorialActivity extends AppCompatActivity {


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

         // Button navigates to the next slide

        ImageButton imgBtnNext = findViewById(R.id.btnNext);
        imgBtnNext.setOnClickListener(view -> startActivity(new Intent(this, TutorialHomePlusActivity.class)));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        setUsername(prefs);



    }
    /**
     * Function which shows the intro text with users name
     * @param prefs - Shared Preference
     */
    private void setUsername(SharedPreferences prefs) {
        String userName = prefs.getString("Name", "");
        TextView prefName = findViewById(R.id.txtTutorialName);
        userName += ",";
        prefName.setText(userName);
    }

}