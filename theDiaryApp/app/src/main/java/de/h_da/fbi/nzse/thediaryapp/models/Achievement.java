package de.h_da.fbi.nzse.thediaryapp.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Class which represents one Achievement
 * Example of Achievemnt :
 * name = Win, Win, Win
 * description = Be happy three times
 *
 */

@Entity
public class Achievement {
    @PrimaryKey
    private long achievement_id;
    @ColumnInfo(name = "achievement_image")
    private int achievementImage;
    @ColumnInfo(name = "achievement_image_selected")
    private String achievementName;
    private String description;
    private boolean isDone;


    public Achievement(){}

    @Ignore
    public Achievement(long achievement_id, int achievementImage, String achievementName, String description, boolean isDone) {
        this.achievement_id = achievement_id;
        this.achievementImage = achievementImage;
        this.achievementName = achievementName;
        this.description = description;
        this.isDone = isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    public long getAchievement_id() {
        return achievement_id;
    }

    public void setAchievement_id(long achievement_id) {
        this.achievement_id = achievement_id;
    }

    public int getAchievementImage() {
        return achievementImage;
    }

    public void setAchievementImage(int achievementImage) {
        this.achievementImage = achievementImage;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
