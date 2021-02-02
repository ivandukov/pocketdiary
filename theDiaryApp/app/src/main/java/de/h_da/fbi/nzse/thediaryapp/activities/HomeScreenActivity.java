package de.h_da.fbi.nzse.thediaryapp.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.activities.entry.NewEntryActivity;
import de.h_da.fbi.nzse.thediaryapp.adapters.EntriesAdapter;
import de.h_da.fbi.nzse.thediaryapp.models.EntryWithActions;
import de.h_da.fbi.nzse.thediaryapp.models.ReminderBroadcast;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class HomeScreenActivity extends AppCompatActivity {


    private List<EntryWithActions> entryWithActionsList;


    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        this.finishAffinity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        RoomDatabase db = RoomDatabase.getDatabase(this);
        FloatingActionButton floatingActionButton = findViewById(R.id.newEntryActionBtn);
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBarHome);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean areSettingChanged = prefs.getBoolean("AreSettingsChanged", false);

        if (areSettingChanged) {
            notificationHandler();
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("AreSettingsChanged",false);
            edit.apply();
        }

        setAppBarUsername(prefs);

        bottomAppBar.setOnMenuItemClickListener(menuItem -> {
            int item = menuItem.getItemId();
            if (item == R.id.achievementsBar) {
                Intent intent = new Intent(this, AchievementsActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        floatingActionButton.setOnClickListener(view -> startActivity(new Intent(this, NewEntryActivity.class)));

        try {
            entryWithActionsList = db.entryWithActionsDao().entrywithActions(); //dirty query
        }
        catch (Exception e){
            Log.d("exception",e.getLocalizedMessage());
            Toast.makeText(this, "Error Retrieving Database", Toast.LENGTH_SHORT).show();
        }


        RecyclerView recyclerView = findViewById(R.id.homeRecyclerView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this); // this = context
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);

        //removes entries which do not have actions
        entryWithActionsList.removeIf(entry -> entry.actionList.size() == 0);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EntriesAdapter(entryWithActionsList,this));
    }


    /**
     * Function which sets the appbar to use the name that the user has already given
     * @param prefs - Shared Prefferences
     */
    private void setAppBarUsername(SharedPreferences prefs) {
        String userName = prefs.getString("Name", "");
        Objects.requireNonNull(getSupportActionBar()).setTitle(userName + "'s Pocket Diary");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(android.R.id.home == id){
            onBackPressed();
        }
        if(R.id.settingsButtonTopBar == id){
            Intent intent = new Intent(HomeScreenActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }


    /**
     * Handles the notification ( not exact )
     * Uses AlarmManager to make an alarm which sends a Broadcast every day at the selected from the user Time( defaults at 20:00 ) to ReminderBroadcast class.
     * There the notifaction is created and sent to the Android Notification API.
     */

    private void notificationHandler(){

        Intent intent = new Intent(this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        int notifHours = prefs.getInt("DatePickerHour", 20);
        int notifMinutes = prefs.getInt("DatePickerMinute", 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,notifHours);
        calendar.set(Calendar.MINUTE, notifMinutes);
        calendar.set(Calendar.SECOND, 0);

        //as of API 19, all repeating alarms are inexact. If your application needs precise delivery times then it must use one-time exact alarms, rescheduling each time as described above.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Log.d("Notification", "Alarm set");
    }




}