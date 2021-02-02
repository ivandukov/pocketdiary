package de.h_da.fbi.nzse.thediaryapp.models;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

/**
 *  Every time a new Entry is created, the Worker is being called, queries the database checks if the achievement condition is achieved,
 *  if it's true then it queries again the database to set the "isDone" on the AchievementTable.
 */
public class RequestWorker extends Worker
{
    private final RoomDatabase db;
    private final Context m_context;


    public RequestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        db = RoomDatabase.getDatabase(context); // Database initialization
        m_context = context;

    }

    @NonNull
    @Override
    public Result doWork()
    {

        List<EntryWithActions> entryWithActionsList = db.entryWithActionsDao().entrywithActions();
        List<Achievement> achievements = db.achievementDao().getAllAchievements();
        entryWithActionsList.removeIf(entry -> entry.actionList.size() == 0);

        Achievement achievementRookie = achievements.get(2);
        Achievement achievement3Entries = achievements.get(3);
        Achievement achievement7Entries = achievements.get(4);

        if(checkEntryNumber(entryWithActionsList.size(), 1) && !achievementRookie.isDone())
        {
            achievementRookie.setDone(true);
            getWorkerToast();
        }
        if(checkEntryNumber(entryWithActionsList.size(), 3) && !achievement3Entries.isDone())
        {
            achievement3Entries.setDone(true);
            getWorkerToast();

        }
        if(checkEntryNumber(entryWithActionsList.size(), 7) && !achievement7Entries.isDone())
        {
            achievement7Entries.setDone(true);
            getWorkerToast();

        }

        db.achievementDao().update(achievementRookie, achievement3Entries, achievement7Entries);

        return Result.success();
    }

    boolean checkEntryNumber(int actualentrysize, int expected)
    {
        return actualentrysize == expected;
    }

    /**
     *  Sets a toast when a new achievement is available.
     */
    void getWorkerToast(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> Toast.makeText(m_context, "New Achievement Available", Toast.LENGTH_LONG).show(), 500 );

    }
}




