package de.h_da.fbi.nzse.thediaryapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.R;

public class SettingsActivity extends AppCompatActivity {

    private TextInputEditText editName;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefsa = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        editName = findViewById(R.id.textInputEditTextName);
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBarSettings);
        FloatingActionButton btnSave = findViewById(R.id.btnImgSave);
        ImageButton imageButtonHome = findViewById(R.id.imageButtonHomeSettings);
        TimePicker pckTime = findViewById(R.id.timePicker);

        // textfield to change customers name
        editName.setText(prefsa.getString("Name", ""));
        // goes to home activity
        imageButtonHome.setOnClickListener(v -> startActivity(new Intent(this, HomeScreenActivity.class)));


        //bottom app bar - right side - goes to achievementsactivity
        bottomAppBar.setOnMenuItemClickListener(menuItem -> {
            int item = menuItem.getItemId();
            if (item == R.id.achievementsBar) {
                Intent intent = new Intent(this, AchievementsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        setTimeOnPicker(prefsa, pckTime);
        //Saves the customers input and navigates the customer back to the homescreen
        btnSave.setOnClickListener(view -> saveSettings(prefsa, pckTime));
    }


    /**
     * Gets the hours and minutes that are saved from the SharedPreferences and sets them on the TimePicker Widget
     * @param prefsa - Shared Preferences - Preferences Manager
     * @param pckTime - TimePicker Widget
     */
    private void setTimeOnPicker(SharedPreferences prefsa, TimePicker pckTime) {
        int notifHours = prefsa.getInt("DatePickerHour", 20);
        int notifMinutes = prefsa.getInt("DatePickerMinute", 0);
        pckTime.setHour(notifHours);
        pckTime.setMinute(notifMinutes);
    }


    /**
     *  Saves the time of the notification that the user wants and the name of the user to the SharedPreferences so that it can be easily accessed later,
     *  then goes to the HomeScreenActivity
     *
     * @param prefsa - Shared Preferences - Preferences Manager
     * @param pckTime - TimePicker Widget
     */
    private void saveSettings(SharedPreferences prefsa, TimePicker pckTime)
    {
        if (!Objects.requireNonNull(editName.getText()).toString().isEmpty() && editName.getText().toString().length() < 20) {
            Toast.makeText(this, "Deine Einstellungen wurden erfolgreich gespeichert.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this, HomeScreenActivity.class));
            SharedPreferences.Editor edit = prefsa.edit();
            edit.putString("Name", Objects.requireNonNull(editName.getText()).toString());
            edit.putInt("DatePickerHour", pckTime.getHour());
            edit.putInt("DatePickerMinute", pckTime.getMinute());
            edit.putBoolean("AreSettingsChanged",true);
            edit.apply();
        } else {
            Toast.makeText(this, "Bitte gebe einen güligten Benutzernamen an.", Toast.LENGTH_SHORT).show();
        }
    }
}