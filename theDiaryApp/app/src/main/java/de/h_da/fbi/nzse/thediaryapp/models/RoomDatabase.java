package de.h_da.fbi.nzse.thediaryapp.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.h_da.fbi.nzse.thediaryapp.daos.AchievementDao;
import de.h_da.fbi.nzse.thediaryapp.daos.ActionDao;
import de.h_da.fbi.nzse.thediaryapp.daos.ActionEntryDao;
import de.h_da.fbi.nzse.thediaryapp.daos.EntryDao;
import de.h_da.fbi.nzse.thediaryapp.daos.EntryWithActionsDao;
import de.h_da.fbi.nzse.thediaryapp.daos.MoodDao;

@Database(entities = {Action.class,Achievement.class,Mood.class,Entry.class, ActionEntryCrossRef.class},version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract ActionDao actionDao();
    public abstract MoodDao moodDao();
    public abstract EntryDao entryDao();
    public abstract ActionEntryDao actionEntryDao();
    public abstract EntryWithActionsDao entryWithActionsDao();
    public abstract AchievementDao achievementDao();

    public static final String DATABASE_NAME = "diary_db";

    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries() // :(
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
