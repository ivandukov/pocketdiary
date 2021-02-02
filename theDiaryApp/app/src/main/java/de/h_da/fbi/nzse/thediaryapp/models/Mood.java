package de.h_da.fbi.nzse.thediaryapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Class which represents one emotinal state of the user.
 * Examples of Moods: Good, Bad, Great
 */
@Entity
public class Mood implements Serializable {
    @PrimaryKey
    private long mood_id;

    @ColumnInfo(name = "name_of_mood")
    private String nameOfMood;
    @ColumnInfo(name = "image_path")
    private int imagePath;

    public Mood(){}

    @Ignore
    public Mood(long mood_id, String name, int imagePath) {
        this.mood_id = mood_id;
        this.nameOfMood = name;
        this.imagePath = imagePath;
    }

    public long getMood_id() {
        return mood_id;
    }

    public void setMood_id(long mood_id) {
        this.mood_id = mood_id;
    }

    public String getNameOfMood() {
        return nameOfMood;
    }

    public void setNameOfMood(String nameOfMood) {
        this.nameOfMood = nameOfMood;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }
}
