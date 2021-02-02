package de.h_da.fbi.nzse.thediaryapp.activities.welcome;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

import de.h_da.fbi.nzse.thediaryapp.models.Achievement;
import de.h_da.fbi.nzse.thediaryapp.models.Action;
import de.h_da.fbi.nzse.thediaryapp.models.Mood;
import de.h_da.fbi.nzse.thediaryapp.models.ReminderBroadcast;
import de.h_da.fbi.nzse.thediaryapp.models.RoomDatabase;
import de.h_da.fbi.nzse.thediaryapp.R;

public class WelcomeAActivity extends AppCompatActivity {

    private RoomDatabase db;
    private static final String channelId = "notifyUser";
    private SharedPreferences prefs;

    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
        this.finishAffinity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_a);
        db = RoomDatabase.getDatabase(this);
        Objects.requireNonNull(getSupportActionBar()).hide();


        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        ImageButton imgBtnNext = findViewById(R.id.btnNext);
        imgBtnNext.setOnClickListener(view -> iniAppDatabases());



    }

    /**
     * Function which initializes the Database with the predefined Achievements. The function is at this activity, because while the user reads the welcome screen,
     * the database could be easily initialized
     */

    private void iniAchievements() {

        try {
            RoomDatabase.databaseExecutor.execute(() -> {
                db.achievementDao().insert(new Achievement(1, R.drawable.ic_verified_24px, "Tutorial geschafft", "Jetzt kann's los gehen!", true));
                db.achievementDao().insert(new Achievement(2, R.drawable.ic_alarm_on_24px, "Ring! Ring!", "Erinnert dich an die wichtigen Dinge", false));
                db.achievementDao().insert(new Achievement(3, R.drawable.ic_star_24px, "Rookie", "Dein Erster Eintrag!", false));
                db.achievementDao().insert(new Achievement(4, R.drawable.ic_emoji_nature_24px, "Fleißige Biene", "Drei Einträge hintereinander erstellt.", false));
                db.achievementDao().insert(new Achievement(5, R.drawable.ic_timeline_24px, "Wow!", "Sieben Einträge hintereinander", false));
                db.achievementDao().insert(new Achievement(6, R.drawable.ic_timeline_good_24px, "Workaholic!", "Ein Monat Pocket Diary regelmäßig benutzt!", false));
            });
        } catch (Exception e) {
            Log.d("exception", e.getLocalizedMessage());
            Toast.makeText(this, "Error Initializing Achievements", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Create the NotificationChannel, but only on API 26+ because
     * the NotificationChannel class is new and not in the support library
     * It is in this activity because we only need to make this channel only 1 time.
     */
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "notifyUser";
            String description = "Channel for the Pocket Diary Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            Log.d("Notification", "Channel Created");

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    /**
     * Function which initialises the mood table(only on the first program start) so that we can reference it later in the recyclerview.
     */
    private void iniDatabaseWithMoods() {
        try {
            RoomDatabase.databaseExecutor.execute(() -> {
                db.moodDao().insert(new Mood(1, "Excellent", R.drawable.ic_sentiment_laughing_outline_24px));
                db.moodDao().insert(new Mood(2, "Great", R.drawable.ic_sentiment_very_happy_24px_outline));
                db.moodDao().insert(new Mood(3, "Good", R.drawable.ic_sentiment_happy_outlined_24px));
                db.moodDao().insert(new Mood(4, "Neutral", R.drawable.neutral_face_outline));
                db.moodDao().insert(new Mood(5, "UhOh", R.drawable.ic_sentiment_sad_outlined_24px));
                db.moodDao().insert(new Mood(6, "Bad", R.drawable.ic_sentiment_very_unhappy_outline_24px));
            });
        } catch (Exception e) {
            Log.d("exception", e.getLocalizedMessage());
            Toast.makeText(this, "Error Initializing Database", Toast.LENGTH_SHORT).show();
        }


    }



    /**
     * Function which initialises the action table(only on the first program start) so that we can reference it later in the recyclerview.
     */
    private void iniDatabaseWithActions() {
        RoomDatabase.databaseExecutor.execute(() -> {
            db.actionDao().insert(new Action(1,"Gaming",R.drawable.ic_sports_esports_24px));
            db.actionDao().insert(new Action(2,"Love",R.drawable.ic_favorite_24px));
            db.actionDao().insert(new Action(3,"Fast Food",R.drawable.ic_fastfood_24px));
            db.actionDao().insert(new Action(4,"Sport",R.drawable.ic_sports_basketball_24px_1_));
            db.actionDao().insert(new Action(5,"Home",R.drawable.ic_weekend_24px_1));
            db.actionDao().insert(new Action(6,"Friends",R.drawable.ic_groups_24px));
            db.actionDao().insert(new Action(7,"Music",R.drawable.ic_radio_24px));
            db.actionDao().insert(new Action(8,"Shopping",R.drawable.ic_shopping_cart_24px_1));
            db.actionDao().insert(new Action(9,"Work",R.drawable.ic_work_24px));
        });

    }

    /**
     * Handles the notification ( not exact )
     * Uses AlarmManager to make an alarm which sends a Broadcast every day at the selected from the user Time( defaults at 20:00 ) to ReminderBroadcast class.
     * There the notifaction is created and sent to the Android Notification API.
     * Only needed to be called one time. If the settings are changed it will be called again, to create a new AlarmInstance class.
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



    /**
     * Function which initializes all tables in the database ( only 1 time )
     */

    private void iniAppDatabases(){
        boolean isDatabaseInit = prefs.getBoolean("isDatabaseInit",false);

        if (!isDatabaseInit) {
            iniAchievements();
            iniDatabaseWithMoods();
            createNotificationChannel();
            iniDatabaseWithActions();
            notificationHandler();
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("isDatabaseInit", true);
            edit.apply();
        }
        startActivity(new Intent(this, WelcomeBActivity.class));
    }


}