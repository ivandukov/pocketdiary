package de.h_da.fbi.nzse.thediaryapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class which handles every entry of the Diary Book
 */
@Entity(foreignKeys = {@ForeignKey(entity = Mood.class,
parentColumns = "mood_id",
childColumns = "mood_id",
onDelete = ForeignKey.CASCADE)})

public class Entry implements Serializable
{


    @PrimaryKey(autoGenerate = true)
    private long entry_id;

    @ColumnInfo(name = "created_at")
    private LocalDateTime createdAt;
    @ColumnInfo( name = "mood_id")
    private long moodId;
    private String note;


    public Entry() {}


    @Ignore
    public Entry(LocalDateTime date, long mood_id, String note)
    {
        this.createdAt = date;
        this.moodId = mood_id;
        this.note = note;

    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public long getMoodId() {
        return moodId;
    }

    public void setMoodId(long moodId) {
        this.moodId = moodId;
    }

    public long getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(long entry_id) {
        this.entry_id = entry_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
